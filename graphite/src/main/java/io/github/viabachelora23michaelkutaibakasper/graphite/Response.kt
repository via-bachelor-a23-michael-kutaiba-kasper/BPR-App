package io.github.viabachelora23michaelkutaibakasper.graphite

data class Response<T> (val data: Map<String, T>, val errors: Any)
