package com.dygames.androiddi.lifecycle

import com.dygames.androiddi.FakeActivity
import com.dygames.androiddi.FakeDefaultDependency
import com.dygames.androiddi.FakeDependency
import com.dygames.androiddi.FakeViewModel
import com.dygames.di.DependencyInjector.inject
import com.dygames.di.dependencies
import com.dygames.di.error.InjectError
import com.dygames.di.lifecycle
import com.dygames.di.provider
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.reflect.typeOf

class LifecycleWatcherTest {

    @Test(expected = InjectError.ConstructorNoneAvailable::class)
    fun `의존된 생명주기가 초기화되지 않으면 의존 생성에 실패한다`() {
        // given
        dependencies {
            lifecycle<FakeActivity> {
                provider<FakeDependency>(typeOf<FakeDefaultDependency>())
            }
        }

        // when
        inject<FakeViewModel>()
    }

    @Test
    fun `의존된 생명주기가 초기화되면 의존 생성에 성공한다`() {
        // given
        dependencies {
            lifecycle<FakeActivity> {
                provider<FakeDependency>(typeOf<FakeDefaultDependency>())
            }
        }
        FakeActivity().onCreate()

        // when
        val fakeViewModel = inject<FakeViewModel>()

        // then
        assertNotNull(fakeViewModel)
    }
}
