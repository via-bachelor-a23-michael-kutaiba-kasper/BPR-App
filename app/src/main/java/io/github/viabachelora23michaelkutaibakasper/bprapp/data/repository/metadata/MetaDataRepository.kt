package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.metadata

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetCategoriesQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetKeywordsQuery

class MetaDataRepository: IMetadataRepository {

    override suspend fun getKeywords(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetKeywordsQuery()).execute()
        Log.d("ApolloEventClient", "getKeywords: ${response.data?.keywords}")
        return response.data?.keywords?.result?.map { it!! } ?: emptyList()
    }

    override suspend fun getCategories(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetCategoriesQuery()).execute()
        Log.d("ApolloEventClient", "getCategories: ${response.data?.categories}")
        return response.data?.categories?.result?.map { it!! } ?: emptyList()
    }


}