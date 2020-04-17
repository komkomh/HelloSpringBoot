package com.example.demo.requests

import com.example.demo.entities.*
import java.time.LocalDateTime

// 車の詳細検索リクエスト
data class CarDetailSearchRequest(
        val carName: String?,
        val bodyTypes: List<BodyType>?,
        val carPriceFrom: Long?,
        val carPriceTo: Long?,
        val createUserId: Int?,
        val createUserName: String?,
        val createdDateTimeFrom: LocalDateTime?,
        val createdDateTimeTo: LocalDateTime?,
        val updateUserId: Int?,
        val updateDateTimeFrom: LocalDateTime?,
        val updateDateTimeTo: LocalDateTime?,
        val partTypes: List<PartType>?,
        val partName: String?,
        val partPriceFrom: Long?,
        val partPriceTo: Long?) {
}

// 車作成リクエスト
data class CarPostRequest(val name: String, val bodyType: BodyType, val price: Long, val parts: List<PartPostRequest>) {
    fun toEntity(loginUser: User): Car {
        val car = Car(
                null,
                name,
                bodyType,
                price,
                loginUser.id!!,
                LocalDateTime.now(),
                loginUser.id,
                LocalDateTime.now(),
                loginUser,
                mutableListOf()
        )
        car.parts.addAll(parts.map { it.toEntity(car) }.toMutableList())
        return car
    }
}

// 部品作成リクエスト
data class PartPostRequest(val partType: PartType, val name: String, val price: Long) {
    fun toEntity(car: Car): Part {
        return Part(null, car, partType, name, price)
    }
}

// 車更新リクエスト
data class CarPutRequest(val name: String, val price: Long, val parts: List<PartPutRequest>) {
    fun toEntity(loginUser: User, car: Car): Car {
        return Car(
                car.id,
                name,
                car.bodyType,
                price,
                car.createUserId,
                car.createdDateTime,
                loginUser.id!!,
                LocalDateTime.now(),
                loginUser,
                parts.map { it.toEntity(car) }.toMutableList())
    }
}

// 部品更新リクエスト
data class PartPutRequest(val id: Int?, val partType: PartType, val name: String, val price: Long) {
    fun toEntity(car: Car): Part {
        return Part(id, car, partType, name, price)
    }
}