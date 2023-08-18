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

sealed interface SocialLogin {

    fun getIntent(context: Context): Intent

    fun getIdToken(result: ActivityResult): String?

    object Google : SocialLogin {

        override fun getIntent(context: Context): Intent {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.CLIENT_ID)
                .build()

            return GoogleSignIn.getClient(context, gso).signInIntent
        }

        override fun getIdToken(result: ActivityResult): String? {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            val idToken: DefaultLogResult<String> = logRunCatching {
                task.getResult(ApiException::class.java)
                    .idToken ?: throw NoSuchElementException(ID_TOKEN_ERROR)
            }.onFailure {
            }

            return idToken.value
        }
    }

    companion object {
        private const val ID_TOKEN_ERROR = "ID TOKEN을 받아올 수 없습니다."
    }
}
