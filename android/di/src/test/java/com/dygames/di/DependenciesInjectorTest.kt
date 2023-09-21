package com.dygames.di

import com.dygames.DefaultTestCartRepository
import com.dygames.DefaultTestProductRepository
import com.dygames.LocalTestProductDao
import com.dygames.RemoteTestProductDao
import com.dygames.TestCartRepository
import com.dygames.TestID
import com.dygames.TestLocal
import com.dygames.TestName
import com.dygames.TestPerson
import com.dygames.TestProduct
import com.dygames.TestProductDao
import com.dygames.TestProductRepository
import com.dygames.TestQualifierViewModel
import com.dygames.TestQualifierWithFieldViewModel
import com.dygames.TestRemote
import com.dygames.TestViewModel
import com.dygames.di.DependencyInjector.inject
import com.dygames.di.error.InjectError
import junit.framework.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.reflect.typeOf

class DependenciesInjectorTest {
    @Test(expected = InjectError.ConstructorNoneAvailable::class)
    fun `설정한 의존에 의존이 모두 존재하지 않으면 객체 생성에 실패한다`() {
        // given
        dependencies {
            provider<TestCartRepository>(typeOf<DefaultTestCartRepository>())
        }

        // when
        inject<TestViewModel>()

        // then
    }

    @Test
    fun `설정한 의존에 의존이 모두 존재하면 객체 생성에 성공한다`() {
        // given
        dependencies {
            provider<TestCartRepository>(typeOf<DefaultTestCartRepository>())
            provider<TestProductRepository>(typeOf<DefaultTestProductRepository>())
            provider {
                TestProduct(
                    TestName(), TestID()
                )
            }
        }

        // when
        val testViewModel = inject<TestViewModel>()

        // then
        assertNotNull(testViewModel)
    }

    @Test
    fun `설정한 의존에 의존이 존재하지 않더라도 의존 관계의 모든 클래스에 생성자가 존재하면 객체 생성에 성공한다`() {
        // given
        // testProduct의 생성자는 재귀적으로 모두 존재
        dependencies {
            provider<TestCartRepository>(typeOf<DefaultTestCartRepository>())
            provider<TestProductRepository>(typeOf<DefaultTestProductRepository>())
        }

        // when
        val testViewModel = inject<TestViewModel>()

        // then
        assertNotNull(testViewModel)
    }

    @Test
    fun `객체를 생성 할 때, 필드 의존도 주입어야 생성에 성공한다`() {
        // given
        dependencies {
            provider<TestCartRepository>(typeOf<DefaultTestCartRepository>())
            provider<TestProductRepository>(typeOf<DefaultTestProductRepository>())
        }

        // when
        val testViewModel = inject<TestViewModel>()

        // then
        assertNotNull(testViewModel.testFieldPerson)
    }

    @Test
    fun `필드 의존을 주입 할 때, @Injectable이 선언되지 않은 필드에는 의존이 주입되지 않는다`() {
        // given
        dependencies {
            provider<TestCartRepository>(typeOf<DefaultTestCartRepository>())
            provider<TestProductRepository>(typeOf<DefaultTestProductRepository>())
        }

        // when
        val testViewModel = inject<TestViewModel>()

        // then
        assertNull(testViewModel.testFieldProduct)
    }

    @Test
    fun `객체를 생성 할 때, 설정한 @Qualifier에 맞는 의존이 주입된다`() {
        // given
        dependencies {
            qualifier(TestLocal()) {
                provider<TestProductDao>(typeOf<LocalTestProductDao>())
            }
            qualifier(TestRemote()) {
                provider<TestProductDao>(typeOf<RemoteTestProductDao>())
            }
        }

        // when
        val testQualifierViewModel = inject<TestQualifierViewModel>()

        // then
        assertEquals(
            true, testQualifierViewModel.remoteTestProductDao is RemoteTestProductDao
        )
        assertEquals(
            true, testQualifierViewModel.localTestProductDao is LocalTestProductDao
        )
    }

    @Test(expected = InjectError.ConstructorNoneAvailable::class)
    fun `객체를 생성 할 때, 인터페이스 의존에 @Qualifier가 선언된 객체가 없으면 생성에 실패한다`() {
        // given
        dependencies {
            provider<TestProductDao>(typeOf<LocalTestProductDao>())
        }

        // when
        inject<TestQualifierViewModel>()

        // then
    }

    @Test
    fun `Qualifier와 필드 주입이 함께 적용된 객체를 생성한다`() {
        // given
        dependencies {
            provider<TestPerson>(typeOf<TestPerson>())
            qualifier(TestLocal()) {
                provider<TestProductDao>(typeOf<LocalTestProductDao>())
            }
            qualifier(TestRemote()) {
                provider<TestProductDao>(typeOf<RemoteTestProductDao>())
            }
        }

        // when
        val testQualifierWithFieldViewModel = inject<TestQualifierWithFieldViewModel>()

        // then
        assertEquals(
            true, testQualifierWithFieldViewModel.remoteTestProductDao is RemoteTestProductDao
        )
        assertEquals(
            true, testQualifierWithFieldViewModel.localTestProductDao is LocalTestProductDao
        )
        assertNotNull(
            testQualifierWithFieldViewModel.testFieldPerson
        )
        assertEquals(
            true, testQualifierWithFieldViewModel.testFieldLocalProductDao is LocalTestProductDao
        )
    }
}
