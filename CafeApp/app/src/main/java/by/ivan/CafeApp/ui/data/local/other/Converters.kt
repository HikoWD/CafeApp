package by.ivan.CafeApp.ui.data.local.other

import androidx.room.TypeConverter
import by.ivan.CafeApp.ui.data.local.entity.OrderDetailsLocalModel
import com.google.gson.Gson
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromMenuItemsToJSON(menuItemList: MenuItemList): String {
        return Gson().toJson(menuItemList)
    }

    @TypeConverter
    fun fromJSONToMenuItems(json: String): MenuItemList {
        return Gson().fromJson(json,MenuItemList::class.java)
    }
}