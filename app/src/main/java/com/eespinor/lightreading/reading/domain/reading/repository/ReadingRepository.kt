package com.eespinor.lightreading.reading.domain.reading.repository

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {

    suspend fun getReadings(month: Int, year: Int): Flow<Resource<List<Reading>>>

    suspend fun insertReading(
        reading: Reading
    ): Flow<Resource<Void>>

    suspend fun updateReading(reading: Reading): Flow<Resource<Void>>

    suspend fun getReading(id: String): Flow<Resource<Reading>>

}