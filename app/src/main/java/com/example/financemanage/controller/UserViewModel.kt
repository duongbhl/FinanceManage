package com.example.financemanage.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanage.dao.UserDao
import com.example.financemanage.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> get() = _currentUser

    suspend fun createUser(username: String, email: String, password: String):Boolean{
        if(userDao.getUserByEmail(email)!=null||userDao.getUserByUsername(username)!=null) return false
        viewModelScope.launch {
            try {
                userDao.insertUser(User(username = username, email = email, password = password))
                val user = userDao.getUserByEmail(email)
                _currentUser.value = user
            }catch (e:Exception){
                return@launch
            }
        }
        return true
    }
    suspend fun checkLogin(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return if(user != null && user.password == password){
            _currentUser.value = user
            true
        }else{
            false
        }
    }
    suspend fun getUserByEmail(email: String): User? {
        val user = userDao.getUserByEmail(email)
        _currentUser.value=user
        return userDao.getUserByEmail(email)
    }
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
    suspend fun changePassword(email: String, newPassword: String):Boolean {
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            user.password = newPassword
            userDao.UpdateUser(user = user)
            _currentUser.value = user
            return true
        }
        else return false
    }
}