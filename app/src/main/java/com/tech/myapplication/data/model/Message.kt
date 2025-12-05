package com.tech.myapplication.data.model

import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String? = null,
    val imageUrl: String? = null,
    val isSent: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
