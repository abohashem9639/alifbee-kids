package com.alifbee.kidsdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alifbee.kidsdemo.data.local.entity.CourseEntity
import com.alifbee.kidsdemo.data.local.entity.LessonEntity
import com.alifbee.kidsdemo.data.local.entity.LevelEntity
import com.alifbee.kidsdemo.data.local.entity.TargetEntity

@Dao
interface SyllabusDao {

    @Query("SELECT * FROM courses ORDER BY `order`")
    suspend fun getCourses(): List<CourseEntity>

    @Query("SELECT * FROM levels")
    suspend fun getLevels(): List<LevelEntity>

    @Query("SELECT * FROM lessons WHERE levelId = :levelId")
    suspend fun getLessonsForLevel(levelId: Int): List<LessonEntity>

    @Query("SELECT * FROM lessons")
    suspend fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLesson(lessonId: Int): LessonEntity?

    @Query("SELECT * FROM targets WHERE lessonId = :lessonId")
    suspend fun getTargetsForLesson(lessonId: Int): List<TargetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevels(levels: List<LevelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTargets(targets: List<TargetEntity>)

    @Query("DELETE FROM courses")
    suspend fun clearCourses()

    @Query("DELETE FROM levels")
    suspend fun clearLevels()

    @Query("DELETE FROM lessons")
    suspend fun clearLessons()

    @Query("DELETE FROM targets")
    suspend fun clearTargets()

    @Transaction
    suspend fun replaceAll(
        courses: List<CourseEntity>,
        levels: List<LevelEntity>,
        lessons: List<LessonEntity>,
        targets: List<TargetEntity>,
    ) {
        clearCourses()
        clearLevels()
        clearLessons()
        clearTargets()
        insertCourses(courses)
        insertLevels(levels)
        insertLessons(lessons)
        insertTargets(targets)
    }

    @Transaction
    suspend fun clearAll() {
        clearCourses()
        clearLevels()
        clearLessons()
        clearTargets()
    }
}
