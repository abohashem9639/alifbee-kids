package com.alifbee.kidsdemo.data.local

import com.alifbee.kidsdemo.data.local.entity.CourseEntity
import com.alifbee.kidsdemo.data.local.entity.LessonEntity
import com.alifbee.kidsdemo.data.local.entity.LevelEntity
import com.alifbee.kidsdemo.data.local.entity.TargetEntity
import com.alifbee.kidsdemo.data.model.Syllabus
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.model.SyllabusTarget

fun SyllabusCourse.toEntity(): CourseEntity = CourseEntity(
    id = id,
    slug = slug,
    language = language,
    icon = icon,
    title = title,
    description = description,
    order = order,
    levelCount = levelCount,
    isPremium = isPremium,
)

fun SyllabusLevel.toEntity(): LevelEntity = LevelEntity(
    id = id,
    slug = slug,
    title = title,
    description = description,
    order = order,
)

fun SyllabusLesson.toEntity(): LessonEntity = LessonEntity(
    id = id,
    title = title,
    icon = icon,
    order = order,
    slug = slug,
    estimate = estimate,
    isRevise = isRevise,
    levelId = levelId,
    status = status,
)

fun SyllabusTarget.toEntity(): TargetEntity = TargetEntity(
    id = id,
    title = title,
    type = type,
    slug = slug,
    order = order,
    icon = icon,
    unlocked = unlocked,
    lessonId = lessonId,
    levelId = levelId,
    status = status,
)

fun CourseEntity.toDomain(): SyllabusCourse = SyllabusCourse(
    id = id, slug = slug, language = language, icon = icon, title = title,
    description = description, order = order, levelCount = levelCount, isPremium = isPremium,
)

fun LevelEntity.toDomain(): SyllabusLevel = SyllabusLevel(
    id = id, slug = slug, title = title, description = description, order = order,
)

fun LessonEntity.toDomain(): SyllabusLesson = SyllabusLesson(
    id = id, title = title, icon = icon, order = order, slug = slug,
    estimate = estimate, isRevise = isRevise, levelId = levelId, status = status,
)

fun TargetEntity.toDomain(): SyllabusTarget = SyllabusTarget(
    id = id, title = title, type = type, slug = slug, order = order,
    icon = icon, unlocked = unlocked, lessonId = lessonId, levelId = levelId, status = status,
)

fun Syllabus.toEntitiesQuadruple(): SyllabusEntitiesBundle = SyllabusEntitiesBundle(
    courses = courses.map { it.toEntity() },
    levels = levels.map { it.toEntity() },
    lessons = lessons.map { it.toEntity() },
    targets = targets.map { it.toEntity() },
)

data class SyllabusEntitiesBundle(
    val courses: List<CourseEntity>,
    val levels: List<LevelEntity>,
    val lessons: List<LessonEntity>,
    val targets: List<TargetEntity>,
)
