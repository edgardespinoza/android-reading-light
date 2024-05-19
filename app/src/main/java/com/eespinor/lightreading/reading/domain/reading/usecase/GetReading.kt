package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import javax.inject.Inject

class GetReading @Inject constructor(
    private val readingRepository: ReadingRepository
){
    suspend operator fun invoke(id: String) = readingRepository.getReading(id)
}