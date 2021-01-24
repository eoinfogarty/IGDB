package ie.redstar.igdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.redstar.igdb.data.model.GameListModel
import ie.redstar.igdb.data.repository.GameListRepository
import ie.redstar.igdb.data.repository.GameListResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val gameRepository: GameListRepository
) : ViewModel() {

    sealed class State {
        object Initial : State()
        object Empty : State()
        object Loading : State()
        object Refreshing : State()
        object LoadingMore : State()
        object UnAuthorized : State()
        data class Loaded(val gameList: List<GameListModel>) : State()
    }

    private val _state = MutableStateFlow<State>(State.Initial)
    val state: StateFlow<State> = _state

    init {
        load(State.Loading, offset = 0)
    }

    fun refresh() {
        load(State.Refreshing, offset = 0)
    }

    fun loadMore() {
        val currentState = state.value
        if (currentState is State.Loaded) {
            load(State.LoadingMore, offset = currentState.gameList.size)
        }
    }

    private fun load(newState: State, offset: Int) {
        if (state.value == newState) {
            return
        }

        viewModelScope.launch {
            gameRepository
                .getGamesList(offset = offset)
                .onStart { _state.emit(newState) }
                .collect {
                    _state.value = when (it) {
                        is GameListResult.Success -> State.Loaded(it.games)
                        is GameListResult.Error -> {
                            // todo add user feedback for errors
                            val unAuthorized = it.error is HttpException && it.error.code() == 401
                            when {
                                unAuthorized -> {
                                    State.UnAuthorized
                                }
                                it.cachedGames.isEmpty() -> {
                                    State.Empty
                                }
                                else -> {
                                    State.Loaded(it.cachedGames)
                                }
                            }
                        }
                    }
                }
        }
    }
}