package com.example.findu.di

import com.example.findu.data.repositoryimpl.AuthRepositoryImpl
import com.example.findu.data.repositoryimpl.DetailSearchRepositoryImpl
import com.example.findu.data.repositoryimpl.BreedRepositoryImpl
import com.example.findu.data.repositoryimpl.DeviceRepositoryImpl
import com.example.findu.data.repositoryimpl.DummyRepositoryImpl
import com.example.findu.data.repositoryimpl.HomeRepositoryImpl
import com.example.findu.data.repositoryimpl.InformationRepositoryImpl
import com.example.findu.data.repositoryimpl.InterestRepositoryImpl
import com.example.findu.data.repositoryimpl.SearchRepositoryImpl
import com.example.findu.domain.repository.DetailSearchRepository
import com.example.findu.data.repositoryimpl.MyRepositoryImpl
import com.example.findu.domain.repository.BreedRepository
import com.example.findu.data.repositoryimpl.ReportRepositoryImpl
import com.example.findu.data.repositoryimpl.TokenRepositoryImpl
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.DeviceRepository
import com.example.findu.domain.repository.DummyRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.repository.InformationRepository
import com.example.findu.domain.repository.InterestRepository
import com.example.findu.domain.repository.SearchRepository
import com.example.findu.domain.repository.MyRepository
import com.example.findu.domain.repository.TokenRepository
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
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindDetailSearchRepository(detailSearchRepositoryImpl: DetailSearchRepositoryImpl): DetailSearchRepository

    @Binds
    @Singleton
    abstract fun bindBreedRepository(breedRepositoryImpl: BreedRepositoryImpl): BreedRepository
  
    @Binds
    @Singleton
    abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    @Singleton
    abstract fun bindInterestRepository(interestRepositoryImpl: InterestRepositoryImpl): InterestRepository

    @Binds
    @Singleton
    abstract fun bindMyRepository(myRepositoryImpl: MyRepositoryImpl): MyRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindDeviceRepository(deviceRepositoryImpl: DeviceRepositoryImpl): DeviceRepository

    @Binds
    @Singleton
    abstract fun bindInformationRepository(informationRepositoryImpl: InformationRepositoryImpl): InformationRepository
}