package com.flickrs.imagesearch.domain.usecases

import com.flickrs.imagesearch.domain.entities.MappedImageItemModel
import com.flickrs.imagesearch.domain.entities.repositories.ImageSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*Image Search use case*/
class ImageSearchUseCase @Inject constructor(
    private val repository: ImageSearchRepository
) {

    fun execute(query: String?): Flow<List<MappedImageItemModel>> = flow {
        emit(repository.fetchSearchData(query))
    }.flowOn(Dispatchers.IO)

}