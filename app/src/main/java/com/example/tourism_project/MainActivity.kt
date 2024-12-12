package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
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
                CategoryList(categories = listOf(
                    Category(1, "Wisata Alam", R.drawable.bromo),
                    Category(2, "Wisata Pantai", R.drawable.bromo),
                    Category(3, "Wisata Sejarah", R.drawable.bromo)
                ))
            }
        }
    }
}
@Composable
fun CategoryList(categories: List<Category>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category) {
                println("Kategori dipilih: ${category.id}") // Debugging

                // Kirim categoryId ke CategoryDetailActivity
                val intent = Intent(context, CategoryDetailActivity::class.java)
                intent.putExtra("categoryId", category.id)
                context.startActivity(intent)
            }
        }
    }
}



@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Display the local image from drawable
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = category.name, style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Tourism_ProjectTheme {
        CategoryList(categories = listOf(
            Category(1, "Wisata Alam", R.drawable.bromo),
            Category(2, "Wisata Pantai", R.drawable.bromo),
            Category(3, "Wisata Sejarah", R.drawable.bromo)
        ))
    }
}
