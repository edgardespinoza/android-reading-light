package com.eespinor.lightreading.reading.data.repository

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.data.remote.reading.ReadingApi
import com.eespinor.lightreading.reading.data.remote.reading.dto.ReadingDtoRegister
import com.eespinor.lightreading.reading.data.remote.reading.dto.toReading
import com.eespinor.lightreading.reading.data.remote.reading.dto.toReadingDtoRegister
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.reading.repository.ReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReadingRemoteRepository @Inject constructor(
    private val readingApi: ReadingApi
) : ReadingRepository {

    override  fun getReadings(month: Int, year: Int): Flow<Resource<List<Reading>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteReadings = readingApi.getListReading(month, year)
                remoteReadings.let { listings ->
                    emit(Resource.Success(data = listings.map { it.toReading() }
                        .sortedBy { it.room.name }))
                    emit(Resource.Loading(false))
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }

    override  fun insertReading(reading: Reading): Flow<Resource<Void>> {

        return flow {
            try {
                emit(Resource.Loading(true))

                readingApi.addReading(reading.toReadingDtoRegister())

                emit(Resource.Success(null))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occured"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }


    override  fun updateReading(reading: Reading): Flow<Resource<Void>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                readingApi.updateReading(reading.id, reading.toReadingDtoRegister())

                emit(Resource.Success(null))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occured"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }

    override  fun getReading(id: String): Flow<Resource<Reading>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteReading = readingApi.getReading(id)
                remoteReading.let { item ->
                    emit(Resource.Success(data = item.toReading()))
                    emit(Resource.Loading(false))
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error " + e.message))
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }
}

