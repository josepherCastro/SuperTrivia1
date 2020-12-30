package br.edu.ifpr.josepher.supertrivia1.dao

import android.util.Log
import br.edu.ifpr.josepher.supertrivia1.model.ranking.RankingCallBack
import br.edu.ifpr.josepher.supertrivia1.model.ranking.RankingUser
import br.edu.ifpr.josepher.supertrivia1.service.RankingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RankingDAO {
    private val url = "https://super-trivia-server.herokuapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RankingService::class.java)

    fun getAll(finished: (category: List<RankingUser>) -> Unit) {

        service.getAll().enqueue(object : Callback<RankingCallBack> {
            override fun onResponse(call: Call<RankingCallBack>, callBack: Response<RankingCallBack>) {

                if(!callBack.isSuccessful){
                    Log.e("jsonapi", callBack.body().toString())
                }else{
                    val rankingUser = callBack.body()!!
                    Log.e("jsonapi", rankingUser.toString())
                    finished(rankingUser.data.ranking)
                }
            }
            override fun onFailure(call: Call<RankingCallBack>, t: Throwable) {
                Log.e("jsonapi",t.toString())
            }
        })
    }
}