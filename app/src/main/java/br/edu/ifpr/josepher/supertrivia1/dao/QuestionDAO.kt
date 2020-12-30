package br.edu.ifpr.josepher.supertrivia1.dao

import android.util.Log
import br.edu.ifpr.josepher.supertrivia1.model.question.QuestionCallBack
import br.edu.ifpr.josepher.supertrivia1.model.question.QuestionData
import br.edu.ifpr.josepher.supertrivia1.model.question.verify.VerifyCallBack
import br.edu.ifpr.josepher.supertrivia1.model.question.verify.VerifyData
import br.edu.ifpr.josepher.supertrivia1.service.QuestionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDAO {
    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(QuestionService::class.java)

    fun nextQuestion(token: String, finished: (QuestionData) -> Unit) {

        service.nextQuestion(token).enqueue(object : Callback<QuestionCallBack> {
            override fun onResponse(
                call: Call<QuestionCallBack>,
                response: Response<QuestionCallBack>
            ) {

                if (!response.isSuccessful) {
                    Log.e("question1", response.body().toString())

                } else {
                    val question = response.body()!!

                    Log.e("question2", response.body()!!.status)
                    Log.e("array", response.body()!!.data?.problem?.answers.toString())

                    finished(question.data!!)
                }
            }

            override fun onFailure(call: Call<QuestionCallBack>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

    fun existQuestion(token: String, finished: (QuestionData) -> Unit) {

        service.existQuestion(token).enqueue(object : Callback<QuestionCallBack> {
            override fun onResponse(
                call: Call<QuestionCallBack>,
                response: Response<QuestionCallBack>
            ) {

                if (!response.isSuccessful) {
                    Log.e("question", response.body().toString())

                } else {
                    val question = response.body()!!

                    finished(question.data!!)
                }
            }

            override fun onFailure(call: Call<QuestionCallBack>, t: Throwable) {
            }


        })
    }
    fun answerQuestion(answer: Int,token: String, finished: (VerifyData) -> Unit) {

        service.answerQuestion(token,answer).enqueue(object : Callback<VerifyCallBack> {
            override fun onResponse(
                call: Call<VerifyCallBack>,
                response: Response<VerifyCallBack>
            ) {

                if (!response.isSuccessful) {
                    Log.e("verify", response.body().toString())

                } else {
                    val verify = response.body()!!

                    finished(verify.data!!)
                }
            }

            override fun onFailure(call: Call<VerifyCallBack>, t: Throwable) {
            }


        })
    }
}