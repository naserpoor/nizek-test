package ir.alireza.naserpour.nizektest.fragments

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import ir.alireza.naserpour.nizektest.NizekApplication
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.data.User
import ir.alireza.naserpour.nizektest.viewmodels.DataSource
import ir.alireza.naserpour.nizektest.viewmodels.SignupViewModelTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val koin = ApplicationProvider.getApplicationContext<NizekApplication>().koin
        val dataSource = koin.get<DataSource>()
        dataSource.getUser(SignupViewModelTest.VALID_USERNAME) ?: dataSource.addUser(User(SignupViewModelTest.VALID_FULL_NAME,SignupViewModelTest.VALID_USERNAME,SignupViewModelTest.VALID_PASSWORD))
        koin.loadModules(listOf(module {
            single(named("bgTime")) {
                TimeUnit.SECONDS.toMillis(2)
            }
            single(named("fgTime")) {
                TimeUnit.SECONDS.toMillis(5)
            }
        }),true)
    }

    @Test
    @Named("should move to login fragment after foreground timeout")
    fun background_timeout() {
        val navController = spyk(TestNavHostController(
            ApplicationProvider.getApplicationContext()).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.homeFragment)
        })
        val homeFragmentScenario = launchFragment(bundleOf("userName" to SignupViewModelTest.VALID_USERNAME)){
            HomeFragment().apply {
                viewLifecycleOwnerLiveData.observeForever {
                    if(it != null){
                        Navigation.setViewNavController(requireView(), navController)
                    }
                }
            }
        }

        homeFragmentScenario.moveToState(Lifecycle.State.CREATED)

        Thread.sleep(TimeUnit.SECONDS.toMillis(3))

        homeFragmentScenario.moveToState(Lifecycle.State.RESUMED)

        verify { navController.navigate(R.id.action_homeFragment_to_loginFragment) }
    }

    @Test
    @Named("should move to login fragment after foreground timeout")
    fun foreground_timeout() {
        val navController = spyk(TestNavHostController(
            ApplicationProvider.getApplicationContext()).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.homeFragment)
        })
        val homeFragmentScenario = launchFragmentInContainer(bundleOf("userName" to SignupViewModelTest.VALID_USERNAME)){
            HomeFragment().apply {
                viewLifecycleOwnerLiveData.observeForever {
                    if(it != null){
                        Navigation.setViewNavController(requireView(), navController)
                    }
                }
            }
        }

        Thread.sleep(TimeUnit.SECONDS.toMillis(7))

        verify { navController.navigate(R.id.action_homeFragment_to_loginFragment) }
    }

}