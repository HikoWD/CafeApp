package by.ivan.CafeApp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.ivan.CafeApp.data.local.dao.*
import by.ivan.CafeApp.data.local.entity.*
import by.ivan.CafeApp.data.local.other.Converters

@Database(
    entities = [
        CategoryLocalModel::class,
        MenuItemLocalModel::class,
        OrderDetailsLocalModel::class,
        OrderLocalModel::class,
        TableLocalModel::class,
        TableVersionLocalModel::class,
        CartItemLocalModel::class,
        SearchHistoryItemLocalModel::class],
    version = 1
)
@TypeConverters(Converters::class) //todo
abstract class CafeDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun orderDao(): OrderDao
    abstract fun tableDao(): TableDao
    abstract fun tableVersionDao(): TableVersionDao
    abstract fun cartItemDao(): CartDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}