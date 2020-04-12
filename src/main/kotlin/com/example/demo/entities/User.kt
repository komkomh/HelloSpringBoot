package com.example.demo.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

// ユーザを表現する
@Entity
@Table(name = "users")
data class User(
        // ユーザID
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,
        // ユーザ名
        @Column(length = 100, nullable = false)
        val name: String
) : Serializable
