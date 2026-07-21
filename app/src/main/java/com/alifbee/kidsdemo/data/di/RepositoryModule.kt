package com.alifbee.kidsdemo.data.di

import com.alifbee.kidsdemo.data.repository.AppsRepository
import com.alifbee.kidsdemo.data.repository.FakeHomescreenRepository
import com.alifbee.kidsdemo.data.repository.FakeReviewRepository
import com.alifbee.kidsdemo.data.repository.HomescreenRepository
import com.alifbee.kidsdemo.data.repository.RemoteAppsRepository
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppsRepository(impl: RemoteAppsRepository): AppsRepository

    @Binds
    @Singleton
    abstract fun bindHomescreenRepository(impl: FakeHomescreenRepository): HomescreenRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(impl: FakeReviewRepository): ReviewRepository
}
