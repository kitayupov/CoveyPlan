package com.kamtayupov.koviplan

import java.io.Serializable

data class Task(var name: String, var subscription: String = "") : Serializable