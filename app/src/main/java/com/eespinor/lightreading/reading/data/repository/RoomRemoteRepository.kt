package com.eespinor.lightreading.reading.data.repository


import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.data.remote.room.RoomApi
import com.eespinor.lightreading.reading.data.remote.room.dto.toRoom
import com.eespinor.lightreading.reading.domain.repository.RoomRepository
import com.eespinor.lightreading.reading.domain.room.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RoomRemoteRepository @Inject constructor(
    private val roomApi: RoomApi
) : RoomRepository {
    override fun getRooms(): Flow<Resource<List<Room>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val remoteRooms = roomApi.getRooms()
                remoteRooms.let { listings ->
                    emit(Resource.Success(data = listings.map { it.toRoom() }))
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