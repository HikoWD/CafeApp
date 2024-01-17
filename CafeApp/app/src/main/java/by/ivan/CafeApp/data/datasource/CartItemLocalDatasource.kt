package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.CartItemDao
import by.ivan.CafeApp.data.local.entity.CartItemLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemLocalDatasource @Inject constructor(private val cartItemDao: CartItemDao) {
    suspend fun getAll(): Flow<List<CartItemLocalModel>> = withContext(Dispatchers.IO) {
        return@withContext cartItemDao.observeAll()
    }

    suspend fun getById(id: Int): Flow<CartItemLocalModel> = withContext(Dispatchers.IO) {
        return@withContext cartItemDao.getById(id = id)
    }

    suspend fun count(id: Int): Int? {
        return cartItemDao.count(id = id)
    }

    suspend fun decreaseCount(id: Int) = withContext(Dispatchers.IO) {
        cartItemDao.decreaseCount(id = id)
    }

    suspend fun add(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO) {
        cartItemDao.add(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun edit(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO) {
        cartItemDao.edit(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun remove(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO){
        cartItemDao.remove(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        cartItemDao.removeAll()
    }
}