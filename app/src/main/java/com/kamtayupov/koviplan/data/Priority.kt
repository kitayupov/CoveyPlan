package com.kamtayupov.koviplan.data

enum class Priority {
    USELESS,
    DEFAULT,
    NORMAL,
    SERIOUS,
    IMPORTANT,
    CRITICAL;

    fun value() = ordinal

    companion object {
        fun get(value: Int) = values()[value]
    }
}