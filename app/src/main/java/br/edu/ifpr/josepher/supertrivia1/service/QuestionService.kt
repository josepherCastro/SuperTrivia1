package br.edu.ifpr.josepher.supertrivia1.service

import br.edu.ifpr.josepher.supertrivia1.model.question.QuestionCallBack
import br.edu.ifpr.josepher.supertrivia1.model.question.verify.VerifyCallBack
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {
    @GET("/problems/next")
    @Headers("charset: utf-8")
    fun nextQuestion(@Header("Authorization") token : String): Call<QuestionCallBack>

    @GET("/problems/view")
    @Headers("charset: utf-8")
    fun existQuestion(@Header("Authorization") token : String): Call<QuestionCallBack>

    @POST("/problems/answer?")
    fun answerQuestion(@Header("Authorization") token: String, @Query("answer") answer: Int): Call<VerifyCallBack>


}