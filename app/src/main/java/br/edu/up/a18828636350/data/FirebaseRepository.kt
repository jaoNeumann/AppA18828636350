package br.edu.up.a18828636350.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Item(
    val id: String = "",
    val name: String = "",
    val description: String = ""
)

class FirebaseRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("items")

    suspend fun createItem(item: Item): Boolean {
        return try {
            val document = collection.document()
            val newItem = item.copy(id = document.id)
            document.set(newItem).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun readItems(): List<Item> {
        return try {
            collection.get().await().map { document ->
                document.toObject(Item::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun updateItem(item: Item): Boolean {
        return try {
            collection.document(item.id).set(item).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteItem(id: String): Boolean {
        return try {
            collection.document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
