package br.edu.up.a18828636350

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.up.a18828636350.ui.ItemScreen
import br.edu.up.a18828636350.ui.theme.AppA18828636350Theme
import br.edu.up.a18828636350.viewmodel.ItemViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ItemViewModel()

        setContent {
            AppA18828636350Theme {
                ItemScreen(viewModel = viewModel)
            }
        }
    }
}
