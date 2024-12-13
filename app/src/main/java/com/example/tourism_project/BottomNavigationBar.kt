import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.tourism_project.AboutActivity
import com.example.tourism_project.CategoryDetailActivity
import com.example.tourism_project.MainActivity
import com.example.tourism_project.PlaceDetailActivity

@Composable
fun BottomNavigationBar(
    currentScreen: String // Untuk menandai layar aktif
) {
    val context = LocalContext.current

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Item Home
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentScreen == "home",
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        )



        // Item PlaceDetail
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "Place Detail") },
            label = { Text("About") },
            selected = currentScreen == "about",
            onClick = {
                val intent = Intent(context, AboutActivity::class.java)
                context.startActivity(intent)
            }
        )
    }
}
