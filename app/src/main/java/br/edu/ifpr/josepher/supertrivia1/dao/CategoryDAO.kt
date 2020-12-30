package br.edu.ifpr.josepher.supertrivia1.dao

import android.util.Log
import br.edu.ifpr.josepher.supertrivia1.model.category.Category
import br.edu.ifpr.josepher.supertrivia1.model.category.CategoryCallBack
import br.edu.ifpr.josepher.supertrivia1.service.CategotyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryDAO {
    private val url = "https://super-trivia-server.herokuapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(CategotyService::class.java)

    fun getAll(finished: (category: List<Category>) -> Unit) {

        service.getAll().enqueue(object : Callback<CategoryCallBack> {
            override fun onResponse(call: Call<CategoryCallBack>, callBack: Response<CategoryCallBack>) {

                if(!callBack.isSuccessful){
                    Log.e("RETORNO_API1", callBack.body().toString())
                }else{
                    val categories = callBack.body()!!
                    Log.e("RETORNO_API2", categories.toString())
                    categories.data?.let { finished(it.categories) }
                }
            }
            override fun onFailure(call: Call<CategoryCallBack>, t: Throwable) {
                Log.e("RETORNO_API3",t.toString())
            }
        })
    }

}