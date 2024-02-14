package com.flickrs.imagesearch.domain.entities.repositories

import com.flickrs.imagesearch.domain.entities.MappedImageItemModel

interface ImageSearchRepository {

    suspend fun fetchSearchData(query: String?): List<MappedImageItemModel>
}