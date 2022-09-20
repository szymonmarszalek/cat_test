package pl.softwarelytest


import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.softwarelytest.Repositories.CatsRepository
import pl.softwarelytest.View.MainFragment
import pl.softwarelytest.ViewModels.MainViewModel
@JvmField
val applicationModule = module {
    viewModel { MainViewModel() }
    single { CatsRepository() }
    single { MainFragment() }
}
