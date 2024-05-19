package com.eespinor.lightreading.reading.domain.room.usecase

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.repository.RoomRepository
import com.eespinor.lightreading.reading.domain.room.model.Room
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRooms @Inject constructor(
    private val roomsRepository: RoomRepository
) {
    suspend operator fun invoke() : Flow<Resource<List<Room>>> = roomsRepository.getRooms()
}