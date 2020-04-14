package com.example.demo.controllers

import com.example.demo.entities.*
import com.example.demo.exceptions.NotFoundException
import com.example.demo.repositories.CarRepository
import org.springframework.web.bind.annotation.*
import java.text.NumberFormat
import java.time.LocalDateTime

@RestController
@RequestMapping("cars")
class CarRestController(private val carRepository: CarRepository) {

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

    // 車表示レスポンス
    data class CarResponse(
            val id: Int,
            val name: String,
            val bodyName: String,
            val price: String,
            val taxRate: Double,
            val taxPrice: String,
            val createdUserName: String,
            val parts: List<PartResponse>) {
        companion object {
            fun create(car: Car): CarResponse {
                return CarResponse(
                        car.id!!,
                        car.name,
                        car.bodyType.view,
                        NumberFormat.getCurrencyInstance().format(car.price),
                        car.bodyType.taxRate,
                        NumberFormat.getCurrencyInstance().format(car.bodyType.getTaxPrice(car.price)),
                        car.createUser.name,
                        car.parts.map { PartResponse.create(it) }
                )
            }
        }
    }

    // 部品表示レスポンス
    data class PartResponse(val partTypeName: String, val name: String, val price: String) {
        companion object {
            fun create(part: Part): PartResponse {
                return PartResponse(
                        part.partType.view,
                        part.name,
                        NumberFormat.getCurrencyInstance().format(part.price))
            }
        }
    }

    @PostMapping("/")
    fun create(@RequestBody request: CarPostRequest): CarResponse {
        return carRepository.save(request.toEntity(getLoginUser()))
                .run { CarResponse.create(this) }
    }

    @GetMapping("/search")
    fun search(): List<CarResponse> {
        return carRepository.findAll().map { car -> CarResponse.create(car) }
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): CarResponse {
        return carRepository.findById(id)
                .map { car -> CarResponse.create(car) }
                .orElseThrow { NotFoundException("ない") }
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

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: CarPutRequest): CarResponse {
        return carRepository.findById(id)
                .map { car -> carRepository.save(request.toEntity(getLoginUser(), car)) }
                .map { car -> CarResponse.create(car) }
                .orElseThrow { NotFoundException("ない") }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        carRepository.deleteById(id)
    }

    // ログイン情報を取得するような処理
    fun getLoginUser(): User {
        return User(1, "おれおれ")
    }
}

