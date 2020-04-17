package com.example.demo.repositories

import com.example.demo.entities.*
import com.example.demo.requests.CarDetailSearchRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.*


@Repository
interface CarRepository : JpaRepository<Car, Int>, CarRepositoryCustom

interface CarRepositoryCustom {
    // 簡易検索
    fun simpleSearch(carId: Int): List<Car>

    // 詳細検索
    fun detailSearch(request: CarDetailSearchRequest): List<Car>
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

    override fun detailSearch(request: CarDetailSearchRequest): List<Car> {
        val builder: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<Car> = builder.createQuery(Car::class.java)
        val root: Root<Car> = query.from(Car::class.java)

        fun <T> builderEqual(path: Path<T>, value: T): Predicate = builder.equal(path, value)

        val params = mutableListOf<Predicate>()
        request.carName?.let { params.add(builder.like(root.get(Car_.name), "%${it}%")) }
        request.bodyTypes?.let { params.add(root.get(Car_.bodyType).`in`(request.bodyTypes)) }

//        request.carPriceFrom?.let { params.add(builder.greaterThan(root.get(Car_.price), it)) }
//        request.carPriceTo?.let { params.add(builder.lessThan(root.get(Car_.price), it)) }
//        request.createUserId?.let { params.add(builderEqual(root.get(Car_.createUser).get(User_.id), it)) }
//        request.createUserName?.let { params.add(builder.like(root.get(Car_.createUser).get(User_.name), "%${it}%")) }
//
//        request.createdDateTimeFrom?.let { params.add(builder.greaterThan(root.get(Car_.createdDateTime), it)) }
//        request.createdDateTimeTo?.let { params.add(builder.lessThan(root.get(Car_.createdDateTime), it)) }
//        request.updateUserId?.let { params.add(builderEqual(root.get(Car_.updateUserId), it)) }
//        request.updateDateTimeFrom?.let { params.add(builder.greaterThan(root.get(Car_.updatedDateTime), it)) }
//        request.updateDateTimeTo?.let { params.add(builder.lessThan(root.get(Car_.updatedDateTime), it)) }

        val subQuery: Subquery<Part> = query.subquery(Part::class.java)
        val dept: Root<Part> = subQuery.from(Part::class.java)
//        request.bodyTypes?.let { params.add(dept.get(Part_.partType).`in`(request.partTypes)) }
        request.partName?.let { params.add(builder.like(dept.get(Part_.name), "%${it}%")) }
//        val partName: String?,
//        val partPriceFrom: Long?,
//        val partPriceTo: Long?


        query.where(*params.toTypedArray())
        return em.createQuery(query).resultList
    }
}