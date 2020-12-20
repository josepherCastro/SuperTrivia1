package br.edu.ifpr.josepher.supertrivia1.dao

import android.util.Log
import br.edu.ifpr.josepher.supertrivia1.model.game.Game
import br.edu.ifpr.josepher.supertrivia1.model.game.GameCallBack
import br.edu.ifpr.josepher.supertrivia1.model.game.endgame.EndGameCallBack
import br.edu.ifpr.josepher.supertrivia1.model.game.endgame.EndGameData
import br.edu.ifpr.josepher.supertrivia1.service.GameService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDAO {
    private val url = "https://super-trivia-server.herokuapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GameService::class.java)!!

    fun startGame(token: String, finished: ((Game)) -> Unit) {

        service.startGame(token).enqueue(object : Callback<GameCallBack> {

            override fun onResponse(call: Call<GameCallBack>, response: Response<GameCallBack>) {

                if (!response.isSuccessful) {
                    Log.e("aqui", response.body().toString())
                } else {
                    val game = response.body()!!
                    //iniciando o jogo
                    if(game.status == "success"){
                        Log.i("O_JOGO",game.status )
                        finished(game.data.game)
                    }else{
                        Log.i("O_JOGO", "Deu ruim no jogo" )
                    }
                }
            }

            override fun onFailure(call: Call<GameCallBack>, t: Throwable) {
                Log.e("RETORNO_API", t.toString())
            }


        })
    }
    fun startGameWhitSetup(token: String, difficulty:String, category_id: Long?, finished: ((Game)) -> Unit) {

        service.startGameWithSetup(token,difficulty,category_id).enqueue(object : Callback<GameCallBack> {

            override fun onResponse(call: Call<GameCallBack>, response: Response<GameCallBack>) {

                if (!response.isSuccessful) {
                    Log.e("aqui", response.body().toString())
                } else {
                    val game = response.body()!!
                    //Iniciando o jogo
                    if(game.status == "success"){
                        Log.i("O_JOGO",game.status )
                        finished(game.data.game)
                    }else{
                        Log.i("O_JOGO", "Deu ruim no jogo" )
                    }
                }
            }
            override fun onFailure(call: Call<GameCallBack>, t: Throwable) {
                Log.e("RETORNO_API", t.toString())
            }
        })
    }
    fun endGame(token: String, finished: ((EndGameData)) -> Unit) {

        service.endGame(token).enqueue(object : Callback<EndGameCallBack> {

            override fun onResponse(call: Call<EndGameCallBack>, response: Response<EndGameCallBack>) {

                if (!response.isSuccessful) {
                    Log.e("O_JOGO", response.body().toString())
                } else {
                    val end = response.body()!!
                    finished(end.data)
                }
            }

            override fun onFailure(call: Call<EndGameCallBack>, t: Throwable) {
                Log.e("RETORNO_API", t.toString())
            }
        })
    }
}