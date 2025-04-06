package del.ifs22023.portofolioku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import del.ifs22023.portofolioku.ui.Beranda.BerandaAdaptiveScreen
import del.ifs22023.portofolioku.ui.BottomNavigationBar
import del.ifs22023.portofolioku.ui.NavigationRailBar
import del.ifs22023.portofolioku.ui.kontak.KontakAdaptiveScreen
import del.ifs22023.portofolioku.ui.portofolio.PortofolioScreen
import del.ifs22023.portofolioku.ui.tentang.TentangSayaScreen
import del.ifs22023.portofolioku.ui.theme.PortofoliokuTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PortofoliokuTheme {
                val navController = rememberNavController()
                // Dapatkan windowSizeClass
                val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(this)
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                    // Tampilan Compact: menggunakan BottomNavigationBar
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Routes.BERANDA,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Routes.BERANDA) {
                                BerandaAdaptiveScreen(
                                    navController = navController,
                                    activity = this@MainActivity
                                )
                            }
                            composable(Routes.PORTOFOLIO) {
                                PortofolioScreen(navController = navController, modifier = Modifier)
                            }
                            composable(Routes.TENTANG_SAYA) {
                                TentangSayaScreen(navController = navController, modifier = Modifier)
                            }
                            composable(Routes.KONTAK) {
                                KontakAdaptiveScreen(navController = navController, modifier = Modifier)
                            }
                        }
                    }
                } else {
                    // Tampilan Medium/Expanded: menggunakan NavigationRailBar di sisi kiri
                    Scaffold (
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "PortofolioKu",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF549BC4)
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                            )
                        }
                    ){ innerPadding ->
                        Row(modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                        ) {
                            NavigationRailBar(navController = navController)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
//                                    .padding(innerPadding)
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = Routes.BERANDA,
                                ) {
                                    composable(Routes.BERANDA) {
                                        BerandaAdaptiveScreen(
                                            navController = navController,
                                            activity = this@MainActivity
                                        )
                                    }
                                    composable(Routes.PORTOFOLIO) {
                                        PortofolioScreen(navController = navController, modifier = Modifier)
                                    }
                                    composable(Routes.TENTANG_SAYA) {
                                        TentangSayaScreen(navController = navController, modifier = Modifier)
                                    }
                                    composable(Routes.KONTAK) {
                                        KontakAdaptiveScreen(navController = navController, modifier = Modifier)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    PortofoliokuTheme {
        val navController = rememberNavController()
        // Preview menggunakan tampilan Compact sebagai contoh.
        del.ifs22023.portofolioku.ui.Beranda.BerandaScreenCompact(navController = navController)
    }
}