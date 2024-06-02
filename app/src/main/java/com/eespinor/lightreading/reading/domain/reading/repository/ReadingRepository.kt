package com.eespinor.lightreading.reading.domain.reading.repository

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {

     fun getReadings(month: Int, year: Int): Flow<Resource<List<Reading>>>

     fun insertReading(
        reading: Reading
    ): Flow<Resource<Void>>

     fun updateReading(reading: Reading): Flow<Resource<Void>>

     fun getReading(id: String): Flow<Resource<Reading>>

}