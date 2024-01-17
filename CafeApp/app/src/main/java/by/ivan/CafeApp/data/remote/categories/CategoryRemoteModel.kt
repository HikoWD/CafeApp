package by.ivan.CafeApp.data.remote.categories

import java.io.Serializable

data class CategoryRemoteModelList(
    val items: List<CategoryRemoteModel> = listOf()
) : Serializable {
    data class CategoryRemoteModel(
        val id: Int = 0,
        val title: String? = null
    ) : Serializable
}