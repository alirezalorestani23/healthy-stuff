package com.example.healthystuff.presentation.screen.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthystuff.domain.model.FoodDaySummary
import com.example.healthystuff.domain.model.FoodEntry
import com.example.healthystuff.domain.model.FoodEntrySource
import com.example.healthystuff.domain.model.FoodItem
import com.example.healthystuff.domain.model.MealEstimate
import com.example.healthystuff.domain.repository.AiRepository
import com.example.healthystuff.domain.repository.FoodLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject


data class FoodLogUiState(
    val entries: List<FoodEntry> = emptyList(),
    val daySummary: FoodDaySummary = FoodDaySummary(0, 0, 0, 0)
)

data class AiEstimateUiState(
    val input: String = "",
    val isLoading: Boolean = false,
    val estimate: MealEstimate? = null,
    val error: String? = null
)

@HiltViewModel
class FoodLogViewModel @Inject constructor(
    private val repository: FoodLogRepository,
    private val aiRepository: AiRepository
) : ViewModel() {


    private val zone = ZoneId.systemDefault()
    private val _selectedDate = MutableStateFlow(LocalDate.now(zone))
    val selectedDate = _selectedDate.asStateFlow()

    private fun rangeFor(date: LocalDate): Pair<Long, Long> {
        val start = date.atStartOfDay(zone).toInstant().toEpochMilli()
        val end = date.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()
        return start to end
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    private val entriesFlow = selectedDate.flatMapLatest { date ->
        val (start, end) = rangeFor(date)
        repository.observeEntriesBetween(start, end)
    }

    private val summaryFlow = selectedDate.flatMapLatest { date ->
        val (start, end) = rangeFor(date)
        repository.observeDaySummaryBetween(start, end)
    }


    val uiState: StateFlow<FoodLogUiState> =
        combine(entriesFlow, summaryFlow) { entries, summary ->
            FoodLogUiState(entries = entries, daySummary = summary)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FoodLogUiState()
        )


    private val _aiState = MutableStateFlow(AiEstimateUiState())
    val aiState = _aiState.asStateFlow()

    fun onAiInputChanged(value: String) {
        _aiState.value = _aiState.value.copy(input = value, error = null)
    }

    fun estimateWithAi() {
        val text = _aiState.value.input.trim()
        if (text.isBlank()) {
            _aiState.value = _aiState.value.copy(error = "Please describe your meal.")
            return
        }
        viewModelScope.launch {
            _aiState.value = _aiState.value.copy(isLoading = true, error = null, estimate = null)
            runCatching {
                aiRepository.estimateMealFromText(text)
            }.onSuccess { estimate ->
                _aiState.value = _aiState.value.copy(isLoading = false, estimate = estimate)
            }.onFailure { t ->
                _aiState.value = _aiState.value.copy(
                    isLoading = false,
                    error = t.message ?: "AI estimate Failed"
                )
            }
        }
    }

    fun clearAiResult() {
        _aiState.value = AiEstimateUiState()
    }


    fun observeEntry(entryId: Long): StateFlow<FoodEntry?> {
        return flow {
            emit(repository.getFoodEntry(entryId))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
    }

    fun addManualEntry(title: String, items: List<FoodItem>) {
        val cleanTitle = title.trim()
        if (cleanTitle.isBlank()) return
        if (items.isEmpty()) return

        val totalCalories = items.sumOf { it.calories }
        val totalProteinG = items.sumOf { it.proteinG }
        val totalCarbsG = items.sumOf { it.carbsG }
        val totalFatG = items.sumOf { it.fatG }

        viewModelScope.launch {
            val entry = FoodEntry(
                id = 0,
                timestampMillis = System.currentTimeMillis(),
                title = cleanTitle,
                source = FoodEntrySource.MANUAL,
                aiNotes = null,
                totalCalories = totalCalories,
                totalProteinG = totalProteinG,
                totalCarbsG = totalCarbsG,
                totalFatG = totalFatG,
                items = items
            )
            repository.insertFoodEntry(entry)
        }
    }

}


