package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TutorialLocalDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.repository.TutorialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultTutorialRepository(
    private val tutorialLocalDataSource: TutorialLocalDataSource
) : TutorialRepository {

    override suspend fun save(tutorialCompleted: Boolean): LogResult<Unit> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                tutorialLocalDataSource.save(tutorialCompleted)
            }
        }

    override suspend fun fetch(): LogResult<Boolean> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                tutorialLocalDataSource.fetch()
            }
        }
}
