package br.edu.up.a18828636350.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.up.a18828636350.viewmodel.ItemViewModel
import br.edu.up.a18828636350.data.Item

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemScreen(viewModel: ItemViewModel) {
    val items by viewModel.items.collectAsState()
    val status by viewModel.status.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { // Inicio do Scaffold Content
        Column(modifier = Modifier.padding(16.dp)) {
            // Exibe o status
            Text(text = "Status: $status")

            // Lista de itens
            LazyColumn {
                items(items) { item ->
                    EditableItemRow(item, viewModel)
                }
            }

            // Botão para adicionar novo item
            Button(onClick = {
                viewModel.addItem(name = "New Item", description = "Description")
            }) {
                Text("Add Item")
            }
        }
    } // Fim do Scaffold Content
}

@Composable
fun EditableItemRow(item: Item, viewModel: ItemViewModel) {
    // Variáveis de estado para edição
    var name by remember { mutableStateOf(item.name) }
    var description by remember { mutableStateOf(item.description) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Campos de edição para o nome e descrição
        Column(modifier = Modifier.weight(1f)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botão para salvar a edição
        Button(onClick = {
            // Atualiza o item com os novos dados
            viewModel.updateItem(item.copy(name = name, description = description))
        }) {
            Text("Save")
        }

        // Botão para excluir o item
        IconButton(onClick = { viewModel.deleteItem(item.id) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
