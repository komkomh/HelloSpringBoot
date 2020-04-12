package com.example.demo.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

// 部品を表現する
@Entity
@Table(name = "parts")
data class Part(
        // 部品タイプ, 車ID
        @EmbeddedId
        val partId: PartKey,
        // 名前
        @Column(length = 100, nullable = false)
        val name: String,
        // 価格
        @Column(nullable = false)
        val price: Long
) : Serializable
