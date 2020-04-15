package com.example.demo.controllers

import com.example.demo.entities.BodyType
import com.example.demo.entities.User
import com.example.demo.exceptions.NotFoundException
import com.example.demo.repositories.CarRepository
import com.example.demo.requests.CarPostRequest
import com.example.demo.requests.CarPutRequest
import com.example.demo.responses.CarResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cars")
class CarRestController(private val carRepository: CarRepository) {

    @GetMapping("/simple_search/{carId}")
    fun simpleSearch(@PathVariable carId: Int): List<CarResponse> {
        return carRepository.simpleSearch(carId)
                .map { car -> CarResponse.create(car) }
    }

    @PostMapping("/")
    fun create(@RequestBody request: CarPostRequest): CarResponse {
        return carRepository.save(request.toEntity(getLoginUser()))
                .run { CarResponse.create(this) }
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): CarResponse {
        return carRepository.findById(id)
                .map { car -> CarResponse.create(car) }
                .orElseThrow { NotFoundException("ない") }
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

