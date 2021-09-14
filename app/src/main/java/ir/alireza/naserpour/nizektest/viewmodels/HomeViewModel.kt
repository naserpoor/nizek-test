package ir.alireza.naserpour.nizektest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.math.abs

class HomeViewModel(
    private val dataSource: DataSource,
    application: Application,
    username:String,
    private val foregroundTime:Long,
    private val backgroundTime:Long
) : AndroidViewModel(application) {

    private var currentTime = 0L
    val fullName = flow {
        emit(dataSource.getUser(username)
            ?.fullName?:"")
    }.asLiveData()

    val loginTimerScreenOn = flow {
        delay(foregroundTime)
        emit(Unit)
    }.asLiveData()

    fun shouldCloseNow():Boolean {
        if (currentTime != 0L && abs(System.currentTimeMillis() - currentTime) > backgroundTime) {
            return true
        }
        return false
    }

    fun appMovedToBackground(){
        currentTime = System.currentTimeMillis()
    }
}