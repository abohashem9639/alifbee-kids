package com.alifbee.kidsdemo.data.local

import android.content.Context
import androidx.room.Room
import com.alifbee.kidsdemo.data.local.dao.ReviewDao
import com.alifbee.kidsdemo.data.local.dao.SyllabusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlifBeeDatabase =
        Room.databaseBuilder(
            context,
            AlifBeeDatabase::class.java,
            "alifbee.db",
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideSyllabusDao(database: AlifBeeDatabase): SyllabusDao = database.syllabusDao()

    @Provides
    fun provideReviewDao(database: AlifBeeDatabase): ReviewDao = database.reviewDao()
}
