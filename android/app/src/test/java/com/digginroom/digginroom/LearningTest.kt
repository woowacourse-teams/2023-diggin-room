package com.digginroom.digginroom

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Serializable
data class Project(val name: String, val language: String)

class LearningTest {
    @Test
    fun kotlinx_serialization_test() {
        val data = Project("kotlinx.serialization", "Kotlin")
        val string = Json.encodeToString(data)
        assertEquals(string, "{\"name\":\"kotlinx.serialization\",\"language\":\"Kotlin\"}")
        val obj = Json.decodeFromString<Project>(string)
        assertEquals(obj, data)
    }
}
