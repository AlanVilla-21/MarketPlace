package com.example.marketplace.dataclases

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioGuardado(
    val uid: String,
    val email: String
)
