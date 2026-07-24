package com.alifbee.kidsdemo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val slug: String,
    val language: String,
    val icon: String,
    val title: String,
    val description: String,
    val order: Int,
    val levelCount: Int,
    val isPremium: Boolean,
)

@Entity(tableName = "levels")
data class LevelEntity(
    @PrimaryKey val id: Int,
    val slug: String,
    val title: String,
    val description: String,
    val order: Int,
)

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val icon: String,
    val order: Int,
    val slug: String,
    val estimate: Int,
    val isRevise: Boolean,
    val levelId: Int,
    val status: Int,
)

@Entity(tableName = "targets")
data class TargetEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val type: String,
    val slug: String,
    val order: Int,
    val icon: String,
    val unlocked: Boolean,
    val lessonId: Int,
    val levelId: Int,
    val status: Int,
)
