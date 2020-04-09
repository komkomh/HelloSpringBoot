package com.example.demo.controllers

import com.example.demo.entities.BodyType
import com.example.demo.entities.Car
import com.example.demo.exceptions.NotFoundException
import com.example.demo.repositories.CarRepository
import org.springframework.web.bind.annotation.*
import java.text.NumberFormat
import java.time.LocalDateTime

@RestController
@RequestMapping("cars")
class CarRestController(private val carRepository: CarRepository) {

    data class CarPostRequest(val name: String, val bodyType: BodyType, val price: Long) {
        fun toEntity(userId: Long): Car {
            return Car(null, name, bodyType, price, userId, LocalDateTime.now(), userId, LocalDateTime.now())
        }
    }

    data class CarResponse(val id: Long, val name: String, val bodyName: String, val price: String, val taxRate: Double, val taxPrice: String) {
        companion object {
            fun create(car: Car): CarResponse {
                return CarResponse(
                        car.id!!,
                        car.name,
                        car.bodyType.view,
                        NumberFormat.getCurrencyInstance().format(car.price),
                        car.bodyType.taxRate,
                        NumberFormat.getCurrencyInstance().format(car.bodyType.getTaxPrice(car.price)))
            }
        }
    }

    @PostMapping("/")
    fun create(@RequestBody request: CarPostRequest): CarResponse {
        return carRepository.save(request.toEntity(getLoginUserId()))
                .run { CarResponse.create(this) }
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): CarResponse {
        return carRepository.findById(id)
                .map { car -> CarResponse.create(car) }
                .orElseThrow { NotFoundException("ない") }
    }

    data class CarPutRequest(val name: String, val price: Long) {
        fun toEntity(userId: Long, car: Car): Car {
            return Car(car.id, name, car.bodyType, price, car.createUserId, car.createdDateTime, userId, LocalDateTime.now())
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: CarPutRequest): CarResponse {
        return carRepository.findById(id)
                .map { car -> carRepository.save(request.toEntity(getLoginUserId(), car)) }
                .map { car -> CarResponse.create(car) }
                .orElseThrow { NotFoundException("ない") }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        carRepository.deleteById(id)
    }

    // ログイン情報を取得するような処理
    fun getLoginUserId(): Long {
        return 10
    }
}

