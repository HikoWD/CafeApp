package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.local.dao.OrderDao
import by.ivan.CafeApp.ui.data.local.entity.OrderLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderLocalDatasource @Inject constructor(private val orderDao: OrderDao) {
    suspend fun getAll(): List<OrderLocalModel> = withContext(Dispatchers.IO) {
        return@withContext orderDao.getAll()
    }

    suspend fun saveOrders(orders: List<OrderLocalModel>) = withContext(Dispatchers.IO){
        orderDao.saveOrders(orders)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        orderDao.removeAll()
    }

    suspend fun insert(orderLocalModel: OrderLocalModel) = withContext(Dispatchers.IO) {
        orderDao.insert(orderLocalModel)
    }
}