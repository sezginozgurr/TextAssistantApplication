package com.textassistantapplication.di

import com.textassistantapplication.data.FakeTextService
import com.textassistantapplication.data.TextService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun bindTextService(service: FakeTextService): TextService
}
