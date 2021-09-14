package ir.alireza.naserpour.nizektest.viewmodels

import ir.alireza.naserpour.nizektest.data.User

interface DataSource {
    fun addUser(user: User)
    fun getUser(userName:String):User?
}