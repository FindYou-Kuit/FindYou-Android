package com.kuit.findu.di

import com.kuit.findu.data.datalocal.datasource.DeviceLocalDataSource
import com.kuit.findu.data.datalocal.datasource.DummyLocalDataSource
import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.data.datalocal.datasourceimpl.DeviceLocalDataSourceImpl
import com.kuit.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.kuit.findu.data.datalocal.datasourceimpl.DummyLocalDataSourceImpl
import com.kuit.findu.data.datalocal.datasourceimpl.TokenLocalDataSourceImpl
import com.kuit.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.kuit.findu.data.dataremote.datasourceimpl.BreedRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasource.GptRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.MyRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.kuit.findu.data.dataremote.datasourceimpl.DetailSearchRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.kuit.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.kuit.findu.data.dataremote.datasourceimpl.AuthRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.DummyRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.GptRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.HomeRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.InterestRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.SearchRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.MyRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.NaverRemoteDataSourceImpl
import com.kuit.findu.data.dataremote.datasourceimpl.ReportRemoteDataSourceImpl
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
    abstract fun bindsTokenLocalDataSource(
        tokenLocalDataSourceImpl: TokenLocalDataSourceImpl
    ): TokenLocalDataSource

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

    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsDeviceDataSource(deviceLocalDataSourceImpl: DeviceLocalDataSourceImpl): DeviceLocalDataSource
}