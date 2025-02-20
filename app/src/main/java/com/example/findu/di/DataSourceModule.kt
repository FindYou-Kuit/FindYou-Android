package com.example.findu.di

import com.example.findu.data.datalocal.datasource.DummyLocalDataSource
import com.example.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.example.findu.data.datalocal.datasourceimpl.DummyLocalDataSourceImpl
import com.example.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.example.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.example.findu.data.dataremote.datasourceimpl.BreedRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.datasourceimpl.DetailSearchRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.datasourceimpl.DummyRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.GptRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.HomeRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.InterestRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.SearchRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.MyRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.NaverRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.ReportRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsDummyRemoteDataSource(dummyRemoteDataSourceImpl: DummyRemoteDataSourceImpl): DummyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsBreedRemoteDataSource(breedRemoteDataSourceImpl: BreedRemoteDataSourceImpl): BreedRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsDummyLocalDataSource(dummyLocalDataSourceImpl: DummyLocalDataSourceImpl): DummyLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsHomeRemoteDataSource(homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl): HomeRemoteDataSource
    
    @Binds
    @Singleton
    abstract fun bindsGptRemoteDataSource(gptRemoteDataSourceImpl: GptRemoteDataSourceImpl): GptRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsSearchRemoteDataSource(searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsDetailSearchRemoteDataSource(detailSearchRemoteDataSourceImpl: DetailSearchRemoteDataSourceImpl): DetailSearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsReportRemoteDataSource(reportRemoteDataSourceImpl: ReportRemoteDataSourceImpl): ReportRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsNaverRemoteDataSource(naverRemoteDataSourceImpl: NaverRemoteDataSourceImpl): NaverRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsInterestRemoteDataSource(interestRemoteDataSourceImpl: InterestRemoteDataSourceImpl): InterestRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsMyRemoteDataSource(myRemoteDataSourceImpl: MyRemoteDataSourceImpl): MyRemoteDataSource
}