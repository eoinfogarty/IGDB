package ie.redstar.igdb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.data.repository.GameDetailRepository
import ie.redstar.igdb.data.repository.GameDetailResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(
    private val gameId: Int,
    private val gameRepository: GameDetailRepository
) : ViewModel() {

    sealed class State {
        object Initial : State()
        object Empty : State()
        object Loading : State()
        object UnAuthorized : State()
        data class Loaded(val game: GameDetailModel) : State()
    }

    private val _state = MutableStateFlow<State>(State.Initial)
    val state: StateFlow<State> = _state

    init {
        refresh()
    }

    fun refresh() {
        if (state.value == State.Loading) {
            return
        }

        viewModelScope.launch {
            gameRepository
                .getGameDetails(gameId)
                .onStart { _state.emit(State.Loading) }
                .collect {
                    _state.value = when (it) {
                        is GameDetailResult.Success -> State.Loaded(it.games)
                        is GameDetailResult.Error -> {
                            // todo add user feedback for errors
                            val unAuthorized = it.error is HttpException && it.error.code() == 401
                            when {
                                unAuthorized -> {
                                    State.UnAuthorized
                                }
                                it.cachedGame == null -> {
                                    State.Empty
                                }
                                else -> {
                                    State.Loaded(it.cachedGame)
                                }
                            }
                        }
                    }
                }
        }
    }
}