package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
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
class SignupViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var dataSource: DataSource
    lateinit var viewModel: SignupViewModel
    val app = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun init(){
        dataSource = mockk()
        viewModel = SignupViewModel(dataSource,app)
    }

    @Test
    @Named("should return false on null arguments")
    fun null_arguments(){
        val result = viewModel.registerUser(null,null,null)
        assertFalse(result)
    }


    @Test
    @Named("should show error and return false for invalid full name")
    fun invalid_full_name() {
        val result = viewModel.registerUser(INVALID_FULL_NAME, VALID_USERNAME, VALID_PASSWORD)
        val error = viewModel.fullNameError.getOrAwaitValue()

        assertFalse(result)
        assertNotNull(error)
        assertEquals(error, app.getString(R.string.error_invalid_full_name))
    }

    @Test
    @Named("should show error and return false for invalid username")
    fun invalid_username() {
        val result = viewModel.registerUser(VALID_FULL_NAME, INVALID_USERNAME, VALID_PASSWORD)
        val error = viewModel.userNameError.getOrAwaitValue()

        assertFalse(result)
        assertNotNull(error)
        assertEquals(error, app.getString(R.string.error_invalid_username))
    }

    @Test
    @Named("should show error and return false for invalid password")
    fun invalid_password() {
        val result = viewModel.registerUser(VALID_FULL_NAME, VALID_USERNAME, INVALID_PASSWORD)
        val error = viewModel.passwordError.getOrAwaitValue()

        assertFalse(result)
        assertNotNull(error)
        assertEquals(error, app.getString(R.string.error_invalid_password))
    }

    @Test
    @Named("should remove errors when entering valid input")
    fun all_valid() {
        every { dataSource.addUser(any()) } returns Unit
        var result = viewModel.registerUser(INVALID_FULL_NAME, INVALID_USERNAME, INVALID_PASSWORD)
        assertFalse(result)
        var err1 = viewModel.fullNameError.getOrAwaitValue()
        var err2 = viewModel.userNameError.getOrAwaitValue()
        var err3 = viewModel.passwordError.getOrAwaitValue()

        assertNotNull(err1)
        assertEquals(err1, app.getString(R.string.error_invalid_full_name))
        assertNotNull(err2)
        assertEquals(err2, app.getString(R.string.error_invalid_username))
        assertNotNull(err3)
        assertEquals(err3, app.getString(R.string.error_invalid_password))


        // valid states
        result = viewModel.registerUser(VALID_FULL_NAME, VALID_USERNAME, VALID_PASSWORD)
        err1 = viewModel.fullNameError.getOrAwaitValue()
        err2 = viewModel.userNameError.getOrAwaitValue()
        err3 = viewModel.passwordError.getOrAwaitValue()

        verify { dataSource.addUser(User(VALID_FULL_NAME, VALID_USERNAME, VALID_PASSWORD)) }
        confirmVerified(dataSource)

        assertTrue(result)
        assertNull(err1)
        assertNull(err2)
        assertNull(err3)
    }

    companion object {
        const val INVALID_FULL_NAME = "Ali"
        const val INVALID_USERNAME = "Alireza"
        const val INVALID_PASSWORD = "Salam"

        const val VALID_FULL_NAME = "John Doe"
        const val VALID_USERNAME = "Naserpoor"
        const val VALID_PASSWORD = "Naserpoor"
    }
}