package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import javax.inject.Inject

class UpdateReading @Inject constructor(
    private val repository: ReadingRepository
) {

    suspend operator fun invoke(reading: Reading) = repository.updateReading(reading)

}