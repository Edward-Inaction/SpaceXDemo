package life.inaction.assignment.spacexdemo.di

import life.inaction.assignment.spacexdemo.ui.launch.LaunchDetailViewModel
import life.inaction.assignment.spacexdemo.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODEL_MODULE = module {
    viewModel { MainViewModel(get()) }
    viewModel { LaunchDetailViewModel(get()) }
}
