package ir.alireza.naserpour.nizektest.data

import ir.alireza.naserpour.nizektest.viewmodels.DataSource

class SimpleUsersDataSource : DataSource {
    private val users = mutableMapOf<String,User>()

    override fun addUser(user: User) {
        users[user.userName] = user
    }

    override fun getUser(userName: String): User? {
        return users[userName]
    }

}