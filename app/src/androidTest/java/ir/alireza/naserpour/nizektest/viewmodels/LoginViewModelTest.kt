package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.data.User
import ir.alireza.naserpour.nizektest.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var dataSource: DataSource
    lateinit var viewModel: LoginViewModel
    val app = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun init(){
        dataSource = mockk()
        every { dataSource.getUser(SignupViewModelTest.INVALID_USERNAME) } returns null
        every {
            dataSource.getUser(SignupViewModelTest.VALID_USERNAME)
        } returns User(
            SignupViewModelTest.VALID_FULL_NAME,
            SignupViewModelTest.VALID_USERNAME,
            SignupViewModelTest.VALID_PASSWORD
        )
        viewModel = LoginViewModel(dataSource,app)
    }

    @Test
    @Named("should return false on null arguments")
    fun null_arguments() {
        val result = viewModel.loginUser(null,null)
        assertFalse(result)
    }

    @Test
    @Named("should show error and return false on invalid username")
    fun invalid_username() {
        val result = viewModel.loginUser(SignupViewModelTest.INVALID_USERNAME,SignupViewModelTest.INVALID_PASSWORD)
        val error = viewModel.loginError.getOrAwaitValue()

        verify { dataSource.getUser(SignupViewModelTest.INVALID_USERNAME) }
        confirmVerified(dataSource)

        assertFalse(result)
        assertNotNull(error)
        assertEquals(app.getString(R.string.error_login_invalid_input), error)
    }

    @Test
    @Named("should show error and return false on invalid password")
    fun invalid_password() {
        val result = viewModel.loginUser(SignupViewModelTest.VALID_USERNAME,SignupViewModelTest.INVALID_PASSWORD)
        val error = viewModel.loginError.getOrAwaitValue()

        verify { dataSource.getUser(SignupViewModelTest.VALID_USERNAME) }
        confirmVerified(dataSource)

        assertFalse(result)
        assertNotNull(error)
        assertEquals(app.getString(R.string.error_login_invalid_input), error)
    }


    @Test
    @Named("should return true and null error for valid inputs")
    fun valid_inputs() {
        val result = viewModel.loginUser(SignupViewModelTest.VALID_USERNAME,SignupViewModelTest.VALID_PASSWORD)
        val error = viewModel.loginError.getOrAwaitValue()

        verify { dataSource.getUser(SignupViewModelTest.VALID_USERNAME) }
        confirmVerified(dataSource)

        assertTrue(result)
        assertNull(error)
    }

}