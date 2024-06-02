package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import javax.inject.Inject

class GetReadings @Inject constructor(
    private val readingRepository: ReadingRepository
){
     operator fun invoke(month: Int, year: Int) = readingRepository.getReadings(month, year)
}