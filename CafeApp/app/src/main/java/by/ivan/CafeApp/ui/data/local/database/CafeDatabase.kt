package by.ivan.CafeApp.ui.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.ivan.CafeApp.ui.data.local.dao.*
import by.ivan.CafeApp.ui.data.local.entity.*
import by.ivan.CafeApp.ui.data.local.other.Converters

@Database(
    entities = [
        CategoryLocalModel::class,
        MenuItemLocalModel::class,
        OrderDetailsLocalModel::class,
        OrderLocalModel::class,
        TableLocalModel::class,
        TableVersionLocalModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun orderDao(): OrderDao
    abstract fun tableDao(): TableDao
    abstract fun tableVersionDao(): TableVersionDao
}