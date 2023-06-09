package by.ivan.CafeApp.ui.data.local.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import by.ivan.CafeApp.ui.data.models.Table
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreTable @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Table")
        val tableId = intPreferencesKey("tableId")
        val tableTitle = stringPreferencesKey("tableTitle")
    }

    val getTable: Flow<Table?> = context.dataStore.data.map {
        Table(
            id = it[tableId] ?: 1,
            title = it[tableTitle] ?: "fds",
            state = 0
        )
    }

    suspend fun saveTable(table: Table) {
        context.dataStore.edit {
            it[tableId] = table.id
            it[tableTitle] = table.title
        }
    }
}