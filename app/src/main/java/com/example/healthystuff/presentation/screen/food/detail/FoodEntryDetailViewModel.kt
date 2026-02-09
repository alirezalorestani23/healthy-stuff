package com.example.healthystuff.presentation.screen.food.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthystuff.domain.model.FoodEntry
import com.example.healthystuff.domain.repository.FoodLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FoodEntryDetailUiState(
    val isLoading: Boolean = true,
    val entry: FoodEntry? = null,
    val error: String? = null
)

@HiltViewModel
class FoodEntryDetailViewModel @Inject constructor(
    private val repository: FoodLogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val entryId: Long = checkNotNull(
        savedStateHandle["entryId"]
    )

    private val _uiState = MutableStateFlow(FoodEntryDetailUiState())
    val uiState: StateFlow<FoodEntryDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = FoodEntryDetailUiState(isLoading = true)
            runCatching { repository.getFoodEntry(entryId) }
                .onSuccess { entry ->
                    _uiState.value = FoodEntryDetailUiState(isLoading = false, entry = entry)
                }
                .onFailure { t ->
                    _uiState.value = FoodEntryDetailUiState(
                        isLoading = false,
                        error = t.message ?: "Failed to load entry"
                    )
                }
        }
    }
}