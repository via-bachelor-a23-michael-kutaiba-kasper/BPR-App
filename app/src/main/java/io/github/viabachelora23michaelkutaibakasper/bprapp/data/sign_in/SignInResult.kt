package io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData (
    val userId: String,
    val displayName: String?,
    val profilePicUrl: String?
)


