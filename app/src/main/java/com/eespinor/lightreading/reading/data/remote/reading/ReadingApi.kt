package com.eespinor.lightreading.reading.data.remote.reading

import com.eespinor.lightreading.reading.data.remote.reading.dto.ReadingDtoGet
import com.eespinor.lightreading.reading.data.remote.reading.dto.ReadingDtoRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReadingApi {

    @GET("api/reading")
    suspend fun getListReading(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): List<ReadingDtoGet>

    @POST("api/reading")
    suspend fun addReading(@Body register: ReadingDtoRegister)

    @PUT("api/reading/{id}")
    suspend fun updateReading(@Path("id") id: String, @Body register: ReadingDtoRegister)

    @GET("api/reading/{id}")
    suspend fun getReading(@Path("id") id:String):ReadingDtoGet

}