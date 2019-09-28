package life.inaction.assignment.spacexdemo.di

import life.inaction.assignment.spacexdemo.usercase.FetchLaunchesCase
import life.inaction.assignment.spacexdemo.usercase.FetchRocketCase
import org.koin.dsl.module

val USER_CASE_MODULE = module {
    factory { FetchLaunchesCase(get()) }
    factory { FetchRocketCase(get()) }
}
