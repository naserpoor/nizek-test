package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import ir.alireza.naserpour.nizektest.data.User
import ir.alireza.naserpour.nizektest.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var dataSource: DataSource
    lateinit var viewModel: HomeViewModel
    val app = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun init(){
        dataSource = mockk()
        every {
            dataSource.getUser(SignupViewModelTest.VALID_USERNAME)
        } returns User(
            SignupViewModelTest.VALID_FULL_NAME,
            SignupViewModelTest.VALID_USERNAME,
            SignupViewModelTest.VALID_PASSWORD
        )
        viewModel = HomeViewModel(dataSource,app,SignupViewModelTest.VALID_USERNAME,TimeUnit.SECONDS.toMillis(3),TimeUnit.SECONDS.toMillis(1))
    }

    @Test
    @Named("should show correct full name")
    fun show_full_name() {
        val fullName = viewModel.fullName.getOrAwaitValue()
        assertEquals(SignupViewModelTest.VALID_FULL_NAME,fullName)
    }

    @Test
    @Named("should return true when screen is off more than background time-out")
    fun background_timeout() {
        var result = viewModel.shouldCloseNow()
        assertFalse(result)

        viewModel.appMovedToBackground()
        Thread.sleep(TimeUnit.SECONDS.toMillis(2))

        result = viewModel.shouldCloseNow()
        assertTrue(result)
    }

    @Test
    @Named("should emit unit after foreground time-out")
    fun foreground_timeout() {
        val result = viewModel.loginTimerScreenOn.getOrAwaitValue(4,TimeUnit.SECONDS)
        assertTrue(result == Unit)
    }

}