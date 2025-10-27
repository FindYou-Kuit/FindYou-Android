package com.kuit.findu.di

import com.kuit.findu.data.repositoryimpl.AuthRepositoryImpl
import com.kuit.findu.data.repositoryimpl.DetailSearchRepositoryImpl
import com.kuit.findu.data.repositoryimpl.BreedRepositoryImpl
import com.kuit.findu.data.repositoryimpl.UserInfoRepositoryImpl
import com.kuit.findu.data.repositoryimpl.DummyRepositoryImpl
import com.kuit.findu.data.repositoryimpl.HomeRepositoryImpl
import com.kuit.findu.data.repositoryimpl.InterestRepositoryImpl
import com.kuit.findu.data.repositoryimpl.SearchRepositoryImpl
import com.kuit.findu.domain.repository.DetailSearchRepository
import com.kuit.findu.data.repositoryimpl.MyRepositoryImpl
import com.kuit.findu.domain.repository.BreedRepository
import com.kuit.findu.data.repositoryimpl.ReportRepositoryImpl
import com.kuit.findu.data.repositoryimpl.TokenRepositoryImpl
import com.kuit.findu.domain.repository.AuthRepository
import com.kuit.findu.domain.repository.UserInfoRepository
import com.kuit.findu.domain.repository.DummyRepository
import com.kuit.findu.domain.repository.report.ReportRepository
import com.kuit.findu.domain.repository.HomeRepository
import com.kuit.findu.domain.repository.InterestRepository
import com.kuit.findu.domain.repository.SearchRepository
import com.kuit.findu.domain.repository.MyRepository
import com.kuit.findu.domain.repository.TokenRepository
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
    abstract fun bindUserInfoRepository(userInfoRepository: UserInfoRepositoryImpl): UserInfoRepository
}