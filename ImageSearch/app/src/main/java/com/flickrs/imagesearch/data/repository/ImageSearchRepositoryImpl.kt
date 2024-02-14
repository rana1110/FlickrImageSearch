package com.flickrs.imagesearch.data.repository

import com.flickrs.imagesearch.data.entities.toImageModel
import com.flickrs.imagesearch.domain.entities.MappedImageItemModel
import com.flickrs.imagesearch.domain.entities.repositories.ImageSearchRepository
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ImageSearchRepository {

    override suspend fun fetchSearchData(query: String?): List<MappedImageItemModel> {
        return try {
            val result = networkDataSource.fetchSearchData(query = query)

            if(result.imageItemList.isEmpty()){
                throw IllegalStateException("Empty product list")
            }
           result.imageItemList.map {
                 it.toImageModel()
             }
        } catch (e: Exception) {
            throw e
        }
    }
}