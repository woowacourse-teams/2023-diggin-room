package com.digginroom.digginroom.data.di

import android.content.Context
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.viewmodels.JoinViewModel
import com.digginroom.digginroom.viewmodels.LoginViewModel
import com.digginroom.digginroom.viewmodels.RoomViewModel
import com.digginroom.digginroom.viewmodels.ScrapViewModel

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
                accountRepository = repositoryProvider.accountRepository,
                tokenRepository = repositoryProvider.tokenRepository
            )
        }
    }

    val roomViewModelFactory = viewModelFactory {
        initializer {
            RoomViewModel(
                rooms = mutableListOf(),
                roomRepository = repositoryProvider.roomRepository
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

    companion object {
        private lateinit var instance: ViewModelFactory
        fun getInstance(context: Context): ViewModelFactory {
            if (!::instance.isInitialized) instance = ViewModelFactory(context)
            return instance
        }
    }
}
