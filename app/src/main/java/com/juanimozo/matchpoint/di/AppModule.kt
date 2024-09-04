package com.juanimozo.matchpoint.di

import android.app.Application
import androidx.room.Room
import com.juanimozo.matchpoint.data.database.ResultDatabase
import com.juanimozo.matchpoint.data.repository.ResultDatabaseRepositoryImpl
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.domain.use_cases.GetMatchesUseCase
import com.juanimozo.matchpoint.domain.use_cases.PlayerUseCase
import com.juanimozo.matchpoint.domain.use_cases.UpdateMatchesUseCase
import com.juanimozo.matchpoint.domain.use_cases.ResultUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResultDatabase(app: Application): ResultDatabase {
        return Room.databaseBuilder(
            app, ResultDatabase::class.java, "result.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideResultDatabaseRepository(
        db: ResultDatabase
    ): ResultDatabaseRepository {
        return ResultDatabaseRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideResultUseCases(repository: ResultDatabaseRepository): ResultUseCases {
        return ResultUseCases(
            updateMatchesUseCase = UpdateMatchesUseCase(repository),
            getMatchesUseCase = GetMatchesUseCase(repository),
            playerUseCase = PlayerUseCase(repository)
        )
    }

}