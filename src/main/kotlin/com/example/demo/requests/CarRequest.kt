package com.example.demo.requests

import com.example.demo.controllers.CarRestController
import com.example.demo.entities.*
import java.time.LocalDateTime

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