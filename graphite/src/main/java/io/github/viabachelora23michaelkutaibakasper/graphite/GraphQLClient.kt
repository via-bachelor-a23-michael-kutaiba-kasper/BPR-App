package io.github.viabachelora23michaelkutaibakasper.graphite

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.IllegalArgumentException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

interface GraphQLClient{
    fun <T> post(url: String, request: Query): Response<T>
}

// TODO: Add better error handling
class GraphiteGraphQLClient: GraphQLClient {
    private val httpClient = HttpClient.newBuilder().build()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun <T> post(url: String, request: Query): Response<T> {
        val postRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(request)))
            .build()

        val postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString())
        val statusCode = postResponse.statusCode()
        if (statusCode < 200 || statusCode > 299) {
            throw IllegalArgumentException("Request was malformed: ${postResponse.body()}")
        }

        // NOTE: (mibui 2023-10-07) This is needed, since gson don't know by default
        //                          how to deserialize a generic type.
        val genericResponseDeserializationType = object : TypeToken<Response<T>>() {}.type

        return gson.fromJson(postResponse.body(), genericResponseDeserializationType)
    }

}