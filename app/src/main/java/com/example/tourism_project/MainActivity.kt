package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tourism_project.models.Category
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tourism_ProjectTheme {
                // Menampilkan kategori dengan data Category yang lebih kompleks
                CategoryList(categories = listOf(
                    Category(1, "Alam"),
                    Category(2, "Sejarah"),
                    Category(3, "Pantai")
                ))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryList(categories: List<Category>) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Tourism Guide") }
            )
        },
        content = { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(categories) { category ->
                    CategoryItem(category = category)
                }
            }
        }
    )
}

@Composable
fun CategoryItem(category: Category) {
    val context = LocalContext.current  // Mengambil context dengan benar
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Arahkan ke detail kategori saat diklik
                val intent = Intent(context, CategoryDetailActivity::class.java)  // Menggunakan context yang benar
                intent.putExtra("categoryId", category.id)  // Mengirimkan id kategori
                context.startActivity(intent)  // Menggunakan startActivity() dengan context yang benar
            }
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Tourism_ProjectTheme {
        // Menampilkan kategori dengan data Category yang lebih kompleks
        CategoryList(categories = listOf(
            Category(1, "Alam"),
            Category(2, "Sejarah"),
            Category(3, "Pantai")
        ))
    }
}
