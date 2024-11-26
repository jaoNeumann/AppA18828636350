package br.edu.up.a18828636350.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.a18828636350.data.FirebaseRepository
import br.edu.up.a18828636350.data.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> get() = _items

    private val _status = MutableStateFlow<String>("")
    val status: StateFlow<String> get() = _status

    fun loadItems() {
        viewModelScope.launch {
            _items.value = repository.readItems()
        }
    }

    fun addItem(name: String, description: String) {
        viewModelScope.launch {
            val item = Item(name = name, description = description)
            if (repository.createItem(item)) {
                loadItems()
                _status.value = "Item added successfully"
            } else {
                _status.value = "Failed to add item"
            }
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            if (repository.updateItem(item)) {
                loadItems()
                _status.value = "Item updated successfully"
            } else {
                _status.value = "Failed to update item"
            }
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            if (repository.deleteItem(id)) {
                loadItems()
                _status.value = "Item deleted successfully"
            } else {
                _status.value = "Failed to delete item"
            }
        }
    }
}
