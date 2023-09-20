package com.digginroom.digginroom.data.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.feature.genretaste.GenreTasteViewModel
import com.digginroom.digginroom.feature.join.JoinViewModel
import com.digginroom.digginroom.feature.login.LoginViewModel
import com.digginroom.digginroom.feature.room.RoomViewModel
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel
import com.digginroom.digginroom.feature.splash.SplashViewModel

class ViewModelFactory(context: Context) {
    private val repositoryProvider = RepositoryProvider(context)

    val joinViewModelFactory = viewModelFactory {
        initializer {
            JoinViewModel(
                accountRepository = repositoryProvider.accountRepository
            )
        }
    }

    val loginViewModelFactory = viewModelFactory {
        initializer {
            LoginViewModel(
                accountRepository = repositoryProvider.accountRepository
            )
        }
    }

    val roomViewModelFactory = viewModelFactory {
        initializer {
            RoomViewModel(
                rooms = mutableListOf(),
                roomRepository = repositoryProvider.roomRepository,
                tutorialRepository = repositoryProvider.tutorialRepository
            )
        }
    }

    val scrapViewModelFactory = viewModelFactory {
        initializer {
            ScrapViewModel(
                rooms = mutableListOf(),
                roomRepository = repositoryProvider.roomRepository
            )
        }
    }

    val splashViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            SplashViewModel(
                tokenRepository = repositoryProvider.tokenRepository,
                memberRepository = repositoryProvider.memberRepository
            )
        }
    }

    val genreTasteViewModelFactory: ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                GenreTasteViewModel(
                    genreTasteRepository = repositoryProvider.genreTasteRepository
                )
            }
        }

    val commentViewModelFactory = viewModelFactory {
        initializer {
            CommentViewModel(
                commentRepository = repositoryProvider.commentRepository
            )
        }
    }

    companion object {
        private lateinit var instance: ViewModelFactory
        fun getInstance(context: Context): ViewModelFactory {
            if (!::instance.isInitialized) instance = ViewModelFactory(context)
            return instance
        }
    }
}
