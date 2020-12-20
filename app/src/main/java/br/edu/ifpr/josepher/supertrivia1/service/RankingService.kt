package br.edu.ifpr.josepher.supertrivia1.service

import br.edu.ifpr.josepher.supertrivia1.model.ranking.RankingCallBack
import retrofit2.Call
import retrofit2.http.GET

interface RankingService {

    @GET("/ranking")
    fun getAll(): Call<RankingCallBack>
}