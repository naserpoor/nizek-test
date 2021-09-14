package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.data.User

class SignupViewModel(
    private val usersDataSource:DataSource,
    application: Application
) : AndroidViewModel(application) {
    private val _fullNameError = MutableLiveData<String?>()
    val fullNameError:LiveData<String?> = _fullNameError

    private val _userNameError = MutableLiveData<String?>()
    val userNameError:LiveData<String?> = _userNameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError:LiveData<String?> = _passwordError

    fun registerUser(
        fullName:String?,
        userName:String?,
        password:String?
    ) : Boolean {
        var result = true
        if(fullName == null || userName == null || password == null ){
            return false
        }

        if (fullName.length < 5) {
            _fullNameError.postValue(getApplication<Application>().getString(R.string.error_invalid_full_name))
            result = false
        } else {
            _fullNameError.postValue(null)
        }

        if (userName.length < 8) {
            _userNameError.postValue(getApplication<Application>().getString(R.string.error_invalid_username))
            result = false
        } else {
            _userNameError.postValue(null)
        }

        if (password.length < 8) {
            _passwordError.postValue(getApplication<Application>().getString(R.string.error_invalid_password))
            result = false
        } else {
            _passwordError.postValue(null)
        }

        if (result){
            usersDataSource.addUser(User(
                fullName,userName, password
            ))
        }
        return result
    }
}