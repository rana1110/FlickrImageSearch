package com.flickrs.imagesearch.data.repository

import com.flickrs.imagesearch.data.remote.ApiService
import com.flickrs.imagesearch.data.entities.FlickrResponse
import com.flickrs.imagesearch.domain.entities.MappedImageItemModel
import javax.inject.Inject

/*Network data source to fetch data from server using api service client*/
class NetworkDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchSearchData(query: String?): FlickrResponse = apiService.getSearchResponse(query?: "porcupine")
}