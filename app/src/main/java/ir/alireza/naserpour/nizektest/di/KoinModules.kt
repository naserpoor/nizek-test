package ir.alireza.naserpour.nizektest.di

import ir.alireza.naserpour.nizektest.data.SimpleUsersDataSource
import ir.alireza.naserpour.nizektest.viewmodels.DataSource
import ir.alireza.naserpour.nizektest.viewmodels.HomeViewModel
import ir.alireza.naserpour.nizektest.viewmodels.LoginViewModel
import ir.alireza.naserpour.nizektest.viewmodels.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val module = module {
    single<DataSource> {
        SimpleUsersDataSource()
    }
    viewModel {
        SignupViewModel(get(),get())
    }
    viewModel {
        LoginViewModel(get(),get())
    }
    viewModel {
        HomeViewModel(get(),get(),it.get(),get(named("fgTime")),get(named("bgTime")))
    }
}

val constModules = module {
    single(named("bgTime")) {
        TimeUnit.SECONDS.toMillis(10)
    }
    single(named("fgTime")) {
        TimeUnit.SECONDS.toMillis(30)
    }
}