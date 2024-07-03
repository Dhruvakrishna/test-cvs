package com.flickr.images.di

import com.flickr.images.data.api.FlickrApiService
import com.flickr.images.repository.FlickrRepository
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
    fun provideFlickrRepository(apiService: FlickrApiService): FlickrRepository {
        return FlickrRepository(apiService)
    }
}