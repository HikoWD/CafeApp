package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.CartDao
import by.ivan.CafeApp.data.local.entity.CartItemLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemLocalDatasource @Inject constructor(private val cartDao: CartDao) {
    suspend fun getAll(): Flow<List<CartItemLocalModel>> = withContext(Dispatchers.IO) {
        return@withContext cartDao.observeAll()
    }

    suspend fun getById(id: Int): Flow<CartItemLocalModel> = withContext(Dispatchers.IO) {
        return@withContext cartDao.getById(id = id)
    }

    suspend fun count(id: Int): Int? {
        return cartDao.count(id = id)
    }

    suspend fun decreaseCount(id: Int) = withContext(Dispatchers.IO) {
        cartDao.decreaseCount(id = id)
    }

    suspend fun add(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO) {
        cartDao.add(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun edit(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO) {
        cartDao.edit(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun remove(cartItemLocalModel: CartItemLocalModel) = withContext(Dispatchers.IO){
        cartDao.remove(cartItemLocalModel = cartItemLocalModel)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        cartDao.removeAll()
    }
}