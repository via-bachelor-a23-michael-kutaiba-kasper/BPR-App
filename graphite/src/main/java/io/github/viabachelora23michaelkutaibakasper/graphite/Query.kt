package io.github.viabachelora23michaelkutaibakasper.graphite

/**
 * @param query GraphQL query string. Can be either a mutation or query, e.g: query{todos{title}}
 * @param variables Query params. Should be passed as an anonymous function, e.g.
 *        for the query query($id: Int!){event(id: $id){title}} you would pass object {val id = 1}
 * */
data class Query (val query: String, val variables: Any, val operationName: String? = null)
