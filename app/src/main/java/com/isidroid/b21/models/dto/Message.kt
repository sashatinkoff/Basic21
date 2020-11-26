package com.isidroid.b21.models.dto

import java.io.Serializable
import java.util.*

data class Message(val user: String, val date: Date, val text: String): Serializable