package br.edu.ifpr.josepher.supertrivia1.service

import br.edu.ifpr.josepher.supertrivia1.model.user.User
import br.edu.ifpr.josepher.supertrivia1.model.user.UserCallback
import br.edu.ifpr.josepher.supertrivia1.model.user.UserInput
import br.edu.ifpr.josepher.supertrivia1.model.user.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("/users")
    fun getAll(): Call<List<User>>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: Long): Call<User>


    @POST("/users")
    @Headers("Content-Type: application/json")
    fun insert(@Body userInput: UserInput): Call<UserCallback>

    @DELETE("users/{id}")
    fun delete(@Path("id") id: Long): Call<Void>

    @POST("auth/")
    @Headers("Content-Type: application/json")
    fun login(@Body userInput: UserLogin) : Call<UserCallback>

    @PATCH("users/{id}")
    @Headers("Content-Type: application/json")
    fun update(@Path("id") id: Long, @Body person: User): Call<User>
}