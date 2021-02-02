package com.ghulam_18102194.praktikum14.api

import com.ghulam_18102194.praktikum14.model.Login
import com.ghulam_18102194.praktikum14.model.Message
import com.ghulam_18102194.praktikum14.model.QuoteResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("registration_number") nim: String,
        @Field("password") password: String
    ): Call<Login>
    @GET("api/v1/myquotes")
    fun getMyQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @GET("api/v1/class_quotes")
    fun getClassQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @GET("api/v1/quotes")
    fun getAllQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @FormUrlEncoded
    @POST("api/v1/quotes")
    fun addQuote(
        @Header("Authorization") token:String,
        @Field("name") name: String,
        @Field("description") description: String
    ): Call<Message>
    @FormUrlEncoded
    @PUT("api/v1/quotes/{quote_id}")
    fun updateQuote(
        @Header("Authorization") token:String,
        @Path("quote_id") quote_id: String,
        @Field("name") title: String,
        @Field("description") description: String
    ): Call<Message>
    @DELETE("api/v1/quotes/{quote_id}")
    fun deleteQuote(
        @Header("Authorization") token:String,
        @Path("quote_id") quote_id: String
    ): Call<Message>
}