package com.example.demo.repositories

import com.example.demo.entities.Part
import com.example.demo.entities.PartKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartRepository : JpaRepository<Part, PartKey>