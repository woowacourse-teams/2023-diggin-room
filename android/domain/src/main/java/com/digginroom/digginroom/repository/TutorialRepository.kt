package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult

interface TutorialRepository {

    suspend fun save(tutorialCompleted: Boolean): LogResult<Unit>

    suspend fun fetch(): LogResult<Boolean>
}
