package com.alifbee.kidsdemo.data.di

import com.alifbee.kidsdemo.data.repository.AppsRepository
import com.alifbee.kidsdemo.data.repository.AuthRepository
import com.alifbee.kidsdemo.data.repository.FakeHomescreenRepository
import com.alifbee.kidsdemo.data.repository.FakeMatchPairsRepository
import com.alifbee.kidsdemo.data.repository.HomescreenRepository
import com.alifbee.kidsdemo.data.repository.MatchPairsRepository
import com.alifbee.kidsdemo.data.repository.RemoteAppsRepository
import com.alifbee.kidsdemo.data.repository.RemoteAuthRepository
import com.alifbee.kidsdemo.data.repository.RemoteReviewRepository
import com.alifbee.kidsdemo.data.repository.RemoteSyllabusRepository
import com.alifbee.kidsdemo.data.repository.RemoteUserRepository
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.data.repository.UserRepository
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
    abstract fun bindAuthRepository(impl: RemoteAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSyllabusRepository(impl: RemoteSyllabusRepository): SyllabusRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: RemoteUserRepository): UserRepository

    @Binds
    @Singleton
    abstract fun bindHomescreenRepository(impl: FakeHomescreenRepository): HomescreenRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(impl: RemoteReviewRepository): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindMatchPairsRepository(impl: FakeMatchPairsRepository): MatchPairsRepository
}
