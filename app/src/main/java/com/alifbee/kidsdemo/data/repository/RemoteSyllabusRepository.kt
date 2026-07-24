package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.local.dao.SyllabusDao
import com.alifbee.kidsdemo.data.local.toDomain
import com.alifbee.kidsdemo.data.local.toEntity
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import com.alifbee.kidsdemo.data.remote.safeApiCall
import com.alifbee.kidsdemo.data.result.BaseRes
import com.alifbee.kidsdemo.data.result.map
import com.alifbee.kidsdemo.data.result.onSuccess
import javax.inject.Inject

class RemoteSyllabusRepository @Inject constructor(
    private val api: ApiService,
    private val syllabusDao: SyllabusDao,
) : SyllabusRepository {

    override suspend fun refreshSyllabus(): BaseRes<Unit> =
        safeApiCall { api.getSyllabus(environment = "prod") }
            .map { it.toDomain() }
            .onSuccess { syllabus ->
                syllabusDao.replaceAll(
                    courses = syllabus.courses.map { it.toEntity() },
                    levels = syllabus.levels.map { it.toEntity() },
                    lessons = syllabus.lessons.map { it.toEntity() },
                    targets = syllabus.targets.map { it.toEntity() },
                )
            }
            .map { }

    override suspend fun getCourses(): List<SyllabusCourse> =
        syllabusDao.getCourses().map { it.toDomain() }

    override suspend fun getLevels(): List<SyllabusLevel> =
        syllabusDao.getLevels().map { it.toDomain() }

    override suspend fun getLessonsForLevel(levelId: Int): List<SyllabusLesson> =
        syllabusDao.getLessonsForLevel(levelId).map { it.toDomain() }

    override suspend fun getAllLessons(): List<SyllabusLesson> =
        syllabusDao.getAllLessons().map { it.toDomain() }

    override suspend fun getLesson(lessonId: Int): SyllabusLesson? =
        syllabusDao.getLesson(lessonId)?.toDomain()

    override suspend fun getTargetsForLesson(lessonId: Int): List<SyllabusTarget> =
        syllabusDao.getTargetsForLesson(lessonId).map { it.toDomain() }

    override suspend fun hasCachedSyllabus(): Boolean =
        syllabusDao.getLevels().isNotEmpty()

    override suspend fun clearCache() {
        syllabusDao.clearAll()
    }
}
