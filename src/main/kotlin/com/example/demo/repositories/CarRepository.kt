package com.example.demo.repositories

import com.example.demo.entities.Car
import com.example.demo.entities.Car_
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Repository
interface CarRepository : JpaRepository<Car, Int>, CarRepositoryCustom

interface CarRepositoryCustom {
    fun simpleSearch(carId: Int): List<Car>
}

class CarRepositoryImpl(private val em: EntityManager) : CarRepositoryCustom {
    override fun simpleSearch(carId: Int): List<Car> {
        val builder: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<Car> = builder.createQuery(Car::class.java)
        val root: Root<Car> = query.from(Car::class.java)
        query.where(
                builder.equal(root.get(Car_.id), carId)
        )
        return em.createQuery(query).resultList
    }
}