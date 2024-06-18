package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.reading.model.calculateAmountPaid
import com.eespinor.lightreading.reading.domain.reading.model.calculateDifferenceMeasure
import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetReadings @Inject constructor(
    private val readingRepository: ReadingRepository,
) {
    operator fun invoke(month: Int, year: Int, setting: Setting?): Flow<Resource<List<Reading>>> =
        readingRepository.getReadings(month, year).toProcessable(setting)

}


fun Flow<Resource<List<Reading>>>.toProcessable(setting: Setting?): Flow<Resource<List<Reading>>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Success -> {
                Resource.Success(resource.data?.map { item ->
                    item.copy(
                        amountPaid = calculateAmountPaid(
                            setting?.priceKwh,
                            calculateDifferenceMeasure(
                                measure = item.measure,
                                measurePrevious = item.measurePrevious
                            )
                        ),
                        differenceMeasure = calculateDifferenceMeasure(
                            measure = item.measure,
                            measurePrevious = item.measurePrevious
                        )
                    )
                })
            }

            else -> {
                resource
            }
        }
    }
}
