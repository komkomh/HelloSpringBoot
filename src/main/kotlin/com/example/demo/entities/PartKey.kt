package com.example.demo.entities

import java.io.Serializable
import javax.persistence.Embeddable

// 部品IDを表現する
@Embeddable
data class PartKey(
        // 識別ID
        var partType: PartType,
        // 車ID
        var carId: Int
) : Serializable

// パーツの種類を表現する
enum class PartType(val view: String) {
    Engine("エンジン"),
    Body("車体"),
    Tire("タイヤ"),
    Handle("ハンドル")
}