package ie.redstar.igdb.ui

import ie.redstar.igdb.ui.detail.DetailViewModel
import ie.redstar.igdb.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { (gameId: Int) -> DetailViewModel(gameId, get()) }
}