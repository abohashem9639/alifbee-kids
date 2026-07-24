package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.data.result.BaseRes

interface SyllabusRepository {
    suspend fun refreshSyllabus(): BaseRes<Unit>
    suspend fun getCourses(): List<SyllabusCourse>
    suspend fun getLevels(): List<SyllabusLevel>
    suspend fun getLessonsForLevel(levelId: Int): List<SyllabusLesson>
    suspend fun getAllLessons(): List<SyllabusLesson>
    suspend fun getLesson(lessonId: Int): SyllabusLesson?
    suspend fun getTargetsForLesson(lessonId: Int): List<SyllabusTarget>
    suspend fun hasCachedSyllabus(): Boolean
    suspend fun clearCache()
}
