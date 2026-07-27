package com.alifbee.kidsdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alifbee.kidsdemo.data.local.dao.ReviewDao
import com.alifbee.kidsdemo.data.local.dao.SyllabusDao
import com.alifbee.kidsdemo.data.local.entity.CourseEntity
import com.alifbee.kidsdemo.data.local.entity.LessonEntity
import com.alifbee.kidsdemo.data.local.entity.LevelEntity
import com.alifbee.kidsdemo.data.local.entity.ReviewEntity
import com.alifbee.kidsdemo.data.local.entity.TargetEntity

@Database(
    entities = [
        CourseEntity::class,
        LevelEntity::class,
        LessonEntity::class,
        TargetEntity::class,
        ReviewEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class AlifBeeDatabase : RoomDatabase() {
    abstract fun syllabusDao(): SyllabusDao
    abstract fun reviewDao(): ReviewDao
}
