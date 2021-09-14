package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.alireza.naserpour.nizektest.R

class LoginViewModel(
    private val dataSource: DataSource,
    application: Application
) : AndroidViewModel(application) {

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> = _loginError

    fun loginUser(
        username:String?,
        password:String?
    ) : Boolean {
        if (username == null || password == null) {
            return false
        }

        val user = dataSource.getUser(username)
        if(user == null || user.password != password){
            _loginError.postValue(getApplication<Application>().getString(R.string.error_login_invalid_input))
            return false
        } else {
            _loginError.postValue(null)
        }

        return true
    }
}