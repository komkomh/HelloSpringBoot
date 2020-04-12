package com.example.demo.controllers

import com.example.demo.entities.*
import com.example.demo.exceptions.NotFoundException
import com.example.demo.repositories.CarRepository
import com.example.demo.repositories.PartRepository
import org.springframework.web.bind.annotation.*
import java.text.NumberFormat
import java.time.LocalDateTime

@RestController
@RequestMapping("parts")
class PartRestController(private val partRepository: PartRepository) {

//    data class PartPostRequest(val name: String, val price: Long) {
//        fun toEntity(userId: Int): Part {
//            return Part(null, name, price)
//        }
//    }
//
//    data class PartResponse(val id: Int, val name: String, val bodyName: String, val price: String, val taxRate: Double, val taxPrice: String) {
//        companion object {
//            fun create(car: Car): CarResponse {
//                return CarResponse(
//                        car.id!!,
//                        car.name,
//                        car.bodyType.view,
//                        NumberFormat.getCurrencyInstance().format(car.price),
//                        car.bodyType.taxRate,
//                        NumberFormat.getCurrencyInstance().format(car.bodyType.getTaxPrice(car.price)))
//            }
//        }
//    }

    @PostMapping("/")
    fun create() {
        val partKey = PartKey(PartType.Body, 1)
        val part = Part(partKey, "ハンドル", 100)
        partRepository.save(part)
    }
//
//    @GetMapping("/{id}")
//    fun read(@PathVariable id: Int): CarResponse {
//        return carRepository.findById(id)
//                .map { car -> CarResponse.create(car) }
//                .orElseThrow { NotFoundException("ない") }
//    }
//
//    data class CarPutRequest(val name: String, val price: Long) {
//        fun toEntity(userId: Int, car: Car): Car {
//            return Car(car.id, name, car.bodyType, price, car.createUserId, car.createdDateTime, userId, LocalDateTime.now())
//        }
//    }
//
//    @PutMapping("/{id}")
//    fun update(@PathVariable id: Int, @RequestBody request: CarPutRequest): CarResponse {
//        return carRepository.findById(id)
//                .map { car -> carRepository.save(request.toEntity(getLoginUserId(), car)) }
//                .map { car -> CarResponse.create(car) }
//                .orElseThrow { NotFoundException("ない") }
//    }
//
//    @DeleteMapping("/{id}")
//    fun delete(@PathVariable id: Int) {
//        carRepository.deleteById(id)
//    }
//
//    // ログイン情報を取得するような処理
//    fun getLoginUserId(): Int {
//        return 10
//    }
}

