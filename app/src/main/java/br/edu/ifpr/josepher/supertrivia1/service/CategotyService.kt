package br.edu.ifpr.josepher.supertrivia1.service

import br.edu.ifpr.josepher.supertrivia1.model.category.CategoryCallBack
import retrofit2.Call
import retrofit2.http.GET

interface CategotyService {
    @GET("/categories")
    fun getAll(): Call<CategoryCallBack>
}