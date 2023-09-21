package com.dygames

import com.dygames.di.annotation.Injectable
import com.dygames.di.annotation.Qualifier

interface TestCartRepository
interface TestProductRepository

class TestName
class TestID
class TestProduct(
    private val testName: TestName,
    private val testID: TestID
)

class TestPerson(
    private val testName: TestName,
)

class DefaultTestCartRepository : TestCartRepository
class DefaultTestProductRepository : TestProductRepository

interface TestProductDao
class RemoteTestProductDao : TestProductDao
class LocalTestProductDao : TestProductDao

class TestViewModel(
    private val testCartRepository: TestCartRepository,
    private val testProductRepository: TestProductRepository,
    private val testProduct: TestProduct
) {
    @Injectable
    var testFieldPerson: TestPerson? = null
    var testFieldProduct: TestProduct? = null
}

@Qualifier
annotation class TestLocal

@Qualifier
annotation class TestRemote

class TestQualifierViewModel(
    @TestRemote val remoteTestProductDao: TestProductDao,
    @TestLocal val localTestProductDao: TestProductDao,
)

class TestQualifierWithFieldViewModel(
    @TestRemote val remoteTestProductDao: TestProductDao,
    @TestLocal val localTestProductDao: TestProductDao,
) {
    @Injectable
    var testFieldPerson: TestPerson? = null

    @TestLocal
    @Injectable
    lateinit var testFieldLocalProductDao: TestProductDao
}
