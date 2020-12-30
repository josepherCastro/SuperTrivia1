package br.edu.ifpr.josepher.supertrivia1.dao

import android.util.Log
import br.edu.ifpr.josepher.supertrivia1.model.user.User
import br.edu.ifpr.josepher.supertrivia1.model.user.UserCallback
import br.edu.ifpr.josepher.supertrivia1.model.user.UserInput
import br.edu.ifpr.josepher.supertrivia1.model.user.UserLogin
import br.edu.ifpr.josepher.supertrivia1.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDAO {
    private val url = "https://super-trivia-server.herokuapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(UserService::class.java)

    fun getAll(finished: (users: List<User>) -> Unit) {

        service.getAll().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()!!
                finished(users)
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun login(userLogin: UserLogin, finished: (User) -> Unit) {
        service.login(userLogin).enqueue(object : Callback<UserCallback> {
            override fun onResponse(call: Call<UserCallback>, response: Response<UserCallback>) {
                if(response.body() != null){
                    if(response.isSuccessful) {
                        val user = response.body()!!
                        finished(user.data.user!!)
                    }
                }
            }
            override fun onFailure(call: Call<UserCallback>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun insert(userInput: UserInput, finished: (User) -> Unit) {
        service.insert(userInput ).enqueue(object : Callback<UserCallback> {
            override fun onResponse(call: Call<UserCallback>, response: Response<UserCallback>) {

                if(!response.isSuccessful){
                    Log.e("LOGIN", response.code().toString())
                }else{
                    val user = response.body()!!
                    Log.e("LOGIN", response.code().toString())
                    Log.e("LOGIN", user.status)
                    finished(user.data.user!!)
                }

            }
            override fun onFailure(call: Call<UserCallback>, t: Throwable) {
                Log.e("LOGIN", t.toString())
                Log.e("LOGIN", call.toString())
            }
        })
    }

    fun update(user: User, finished: (User) -> Unit) {
        service.update(user.id!!, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()!!
                finished(user)
            }
            override fun onFailure(call: Call<User>, t: Throwable) { }
        })
    }

    fun get(id: Long, finished: (user: User) -> Unit) {
        service.getUser(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()!!
                finished(user)
            }
            override fun onFailure(call: Call<User>, t: Throwable) { }
        })
    }
}