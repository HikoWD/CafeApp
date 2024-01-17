package by.ivan.CafeApp.data.local.di

import android.content.Context
import androidx.room.Room
import by.ivan.CafeApp.ui.data.local.dao.*
import by.ivan.CafeApp.ui.data.local.database.CafeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideCategoryDao(db: CafeDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Provides
    @Singleton
    fun provideMenuItemDao(db: CafeDatabase): MenuItemDao {
        return db.menuItemDao()
    }

    @Provides
    @Singleton
    fun provideOrderDao(db: CafeDatabase): OrderDao {
        return db.orderDao()
    }

    @Provides
    @Singleton
    fun provideTableDao(db: CafeDatabase): TableDao {
        return db.tableDao()
    }

    @Provides
    @Singleton
    fun provideTableVersion(db: CafeDatabase): TableVersionDao {
        return db.tableVersionDao()
    }

    @Provides
    @Singleton
    fun provideCartItem(db: CafeDatabase): CartItemDao {
        return db.cartItemDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CafeDatabase {
        return Room.databaseBuilder(
            context,
            CafeDatabase::class.java, "CafeDatabase"
        ).build()
    }
}