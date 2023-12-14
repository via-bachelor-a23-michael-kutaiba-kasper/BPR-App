package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.metadata

interface IMetadataRepository {

    suspend fun getKeywords(): List<String>
    suspend fun getCategories(): List<String>
}