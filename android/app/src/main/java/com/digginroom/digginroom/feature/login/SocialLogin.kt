package com.digginroom.digginroom.feature.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.digginroom.digginroom.BuildConfig
import com.digginroom.digginroom.logging.DefaultLogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

sealed interface SocialLogin {

    object Google : SocialLogin {

        fun getIntent(context: Context): Intent {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.CLIENT_ID)
                .requestServerAuthCode(BuildConfig.CLIENT_ID)
                .build()

            return GoogleSignIn.getClient(context, gso).signInIntent
        }

        fun getIdToken(result: ActivityResult): String? {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            val idToken: DefaultLogResult<String> = logRunCatching {
                task.getResult(ApiException::class.java).idToken
                    ?: throw NoSuchElementException(ID_TOKEN_ERROR)
            }.onFailure {
            }

            return idToken.value
        }
    }

    object KaKao : SocialLogin {

        fun getIdToken(context: Context, login: (idToken: String) -> Unit) {
            when (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                true -> loginWithKakaoApplication(context) { login(it) }
                false -> loginWithKakaoAccount(context) { login(it) }
            }
        }

        private fun loginWithKakaoApplication(context: Context, login: (idToken: String) -> Unit) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                getIdToken(token, error)?.let {
                    login(it)
                }
            }
        }

        private fun loginWithKakaoAccount(context: Context, login: (idToken: String) -> Unit) {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                getIdToken(token, error)?.let {
                    login(it)
                }
            }
        }

        private fun getIdToken(token: OAuthToken?, exception: Throwable?): String? {
            if (exception is ClientError && exception.reason == ClientErrorCause.Cancelled) return null
            return token?.idToken
        }
    }

    companion object {
        private const val ID_TOKEN_ERROR = "ID TOKEN을 받아올 수 없습니다."
    }
}
