package com.digginroom.digginroom

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Duration
import java.time.LocalDateTime

class LearningTest {
    @Serializable
    data class Project(val name: String, val language: String)

    @Test
    fun kotlinx_serialization_test() {
        val data = Project("kotlinx.serialization", "Kotlin")
        val string = Json.encodeToString(data)
        assertEquals(string, "{\"name\":\"kotlinx.serialization\",\"language\":\"Kotlin\"}")
        val obj = Json.decodeFromString<Project>(string)
        assertEquals(obj, data)
    }

    @Test
    fun localDateTime_duration_seconds_test() {
        val time1 = LocalDateTime.of(2023, 1, 1, 7, 30)
        val time2 = LocalDateTime.of(2023, 1, 1, 7, 45)
        val durationSeconds = Duration.between(time1, time2).seconds
        assertEquals(900, durationSeconds)
    }
}
