package com.alifbee.kidsdemo.data.remote.mapper

import com.alifbee.kidsdemo.data.model.Syllabus
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.data.remote.dto.SyllabusBody
import com.alifbee.kidsdemo.data.remote.dto.SyllabusCourseDto
import com.alifbee.kidsdemo.data.remote.dto.SyllabusLessonDto
import com.alifbee.kidsdemo.data.remote.dto.SyllabusLevelDto
import com.alifbee.kidsdemo.data.remote.dto.SyllabusTargetDto

fun SyllabusBody.toDomain(): Syllabus = Syllabus(
    courses = courses.orEmpty().map { it.toDomain() },
    levels = levels.orEmpty().map { it.toDomain() },
    lessons = lessons.orEmpty().map { it.toDomain() },
    targets = targets.orEmpty().map { it.toDomain() },
)

private fun SyllabusCourseDto.toDomain(): SyllabusCourse = SyllabusCourse(
    id = id ?: 0,
    slug = slug.orEmpty(),
    language = language.orEmpty(),
    icon = icon.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    order = order ?: 0,
    levelCount = levelCount ?: 0,
    isPremium = isPremium == true,
)

private fun SyllabusLevelDto.toDomain(): SyllabusLevel = SyllabusLevel(
    id = id ?: 0,
    slug = slug.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    order = order ?: 0,
)

private fun SyllabusLessonDto.toDomain(): SyllabusLesson = SyllabusLesson(
    id = id ?: 0,
    title = title.orEmpty(),
    icon = icon.orEmpty(),
    order = order ?: 0,
    slug = slug.orEmpty(),
    estimate = estimate ?: 0,
    isRevise = isRevise == true,
    levelId = levelId ?: 0,
    status = status ?: 0,
)

private fun SyllabusTargetDto.toDomain(): SyllabusTarget = SyllabusTarget(
    id = id ?: 0,
    title = title.orEmpty(),
    type = type.orEmpty(),
    slug = slug.orEmpty(),
    order = order ?: 0,
    icon = icon.orEmpty(),
    unlocked = unlocked == 1,
    lessonId = lessonId ?: 0,
    levelId = levelId ?: 0,
    status = status ?: 0,
)
