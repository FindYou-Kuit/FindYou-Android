package com.example.findu.di

import com.example.findu.data.repositoryimpl.DummyRepositoryImpl
import com.example.findu.data.repositoryimpl.ReportRepositoryImpl
import com.example.findu.domain.repository.DummyRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.data.repositoryimpl.HomeRepositoryImpl
import com.example.findu.domain.repository.HomeRepository
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
    abstract fun bindDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository
}