package com.blink.testwebservice.model

import java.util.Date

data class Student(
    internal val id: Short,
    internal val name: String,
    internal val address: String,
    internal val birthDate: Date
)
