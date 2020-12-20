package br.edu.ifpr.josepher.supertrivia1.service

import br.edu.ifpr.josepher.supertrivia1.model.game.GameCallBack
import br.edu.ifpr.josepher.supertrivia1.model.game.endgame.EndGameCallBack
import retrofit2.Call
import retrofit2.http.*

interface GameService {
    @GET("/games")
    fun startGame(@Header("Authorization") token : String): Call<GameCallBack>

    @GET("/games?")
    fun startGameWithSetup(@Header("Authorization") token: String,
                           @Query("difficulty") difficulty: String,
                           @Query("category_id") category_id: Long?
    ): Call<GameCallBack>

    @DELETE("/games")
    fun endGame(@Header("Authorization") token : String):  Call<EndGameCallBack>
}