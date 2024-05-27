package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import com.eespinor.lightreading.reading.domain.room.model.Room
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddReading @Inject constructor(
    private val repository: ReadingRepository
) {

    suspend operator fun invoke(
        measure: Double,
        year: Int,
        month: Int,
        roomId: String
    ): Flow<Resource<Void>> {


        return repository.insertReading(
            Reading(
                measure = measure,
                year = year,
                month = month,
                room = Room(
                    id = roomId,
                    name = ""
                ),
            )
        )

    }

}