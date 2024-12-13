import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.tourism_project.AboutActivity
import com.example.tourism_project.MainActivity

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
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            },
            label = { Text("Home", color = Color.Black) },
            selected = currentScreen == "home",
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        )

        // Item About
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "About",
                    tint = Color.Black
                )
            },
            label = { Text("About", color = Color.Black) },
            selected = currentScreen == "about",
            onClick = {
                val intent = Intent(context, AboutActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        )
    }
}
