package com.example.demo.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

// 車を表現する
@Entity
@Table(name = "cars")
data class Car(
        // 車ID(識別ID)
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,
        // 車の名前
        @Column(length = 100, nullable = false)
        val name: String,
        // 車種
        @Column(length = 10, nullable = false)
        @Enumerated(EnumType.STRING)
        val bodyType: BodyType,
        // 価格
        @Column(nullable = false)
        val price: Long,
        // 作成者ID
        @Column(nullable = false)
        val createUserId: Int,
        // 作成日時
        @Column(nullable = false)
        val createdDateTime: LocalDateTime,
        // 更新者ID
        @Column(nullable = false)
        val updateUserId: Int,
        // 更新日時
        @Column(nullable = false)
        val updatedDateTime: LocalDateTime
) : Serializable

// ボディタイプを表現する
enum class BodyType(val view: String, val taxRate: Double) {
    Kei("軽自動車", 1.08),
    Sedan("セダン", 1.1),
    Suv("SUV", 1.1),
    Minivan("ミニバン", 1.1),
    Wagon("ワゴン", 1.1);

    // 税込み価格を取得する
    fun getTaxPrice(price: Long): Long {
        val taxPrice = price * taxRate
        return taxPrice.toLong()
    }
}