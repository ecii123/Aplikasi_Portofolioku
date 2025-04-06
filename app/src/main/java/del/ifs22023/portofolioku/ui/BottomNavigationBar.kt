package del.ifs22023.portofolioku.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.lang.reflect.Modifier
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem(
            route = Routes.BERANDA,
            title = "Beranda",
            icon = Icons.Default.Home
        ),
        NavigationItem(
            route = Routes.PORTOFOLIO,
            title = "Portofolio",
            icon = Icons.Default.List
        ),
        NavigationItem(
            route = Routes.TENTANG_SAYA,
            title = "Tentang",
            icon = Icons.Default.Info
        ),
        NavigationItem(
            route = Routes.KONTAK,
            title = "Kontak",
            icon = Icons.Default.Email
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}

@Composable
fun NavigationRailBar(navController: NavController) {
    val items = listOf(
        NavigationItem(
            route = Routes.BERANDA,
            title = "Beranda",
            icon = Icons.Default.Home
        ),
        NavigationItem(
            route = Routes.PORTOFOLIO,
            title = "Portofolio",
            icon = Icons.Default.List
        ),
        NavigationItem(
            route = Routes.TENTANG_SAYA,
            title = "Tentang",
            icon = Icons.Default.Info
        ),
        NavigationItem(
            route = Routes.KONTAK,
            title = "Kontak",
            icon = Icons.Default.Email
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationRail(
        modifier = androidx.compose.ui.Modifier
            .width(200.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationRailItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Row(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp), // Supaya ikon dan teks rata kiri
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                                    modifier = androidx.compose.ui.Modifier.size(32.dp)
                        )
                        Spacer(modifier = androidx.compose.ui.Modifier.width(12.dp))
                        // Jika Anda ingin membedakan warna teks saat dipilih, bisa gunakan kondisi "selected"
                        val textColor = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                        Text(text = item.title, color = textColor, )
                    }
                },
                // Nonaktifkan label default agar tidak ditampilkan di bawah icon
                alwaysShowLabel = false
            )
        }
    }
}


data class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)


