package del.ifs22023.portofolioku.ui.portofolio

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import del.ifs22023.portofolioku.R
import del.ifs22023.portofolioku.ui.theme.PortofoliokuTheme

// Fungsi untuk mendeteksi orientasi device
@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

// Fungsi sederhana untuk mendapatkan WindowWidthSizeClass berdasarkan screenWidthDp
@Composable
fun getWindowWidthClass(): WindowWidthSizeClass {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    return when {
        screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}

// PortofolioScreen dengan adaptive layout
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PortofolioScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val landscape = isLandscape()
    val widthSizeClass = calculateWindowSizeClass(activity = context as androidx.activity.ComponentActivity).widthSizeClass

    // TopAppBar tampil hanya jika device Compact dan dalam portrait
    val showTopBar = widthSizeClass == WindowWidthSizeClass.Compact && !landscape

    // Gunakan grid (2 card per baris) hanya jika device Medium/Expanded dan dalam landscape
    val useGridLayout = (widthSizeClass != WindowWidthSizeClass.Compact) && landscape

    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = {
                        Text(
                            text = "PortofolioKu",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF549BC4)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        },
        content = { innerPadding ->
            val modifierWithPadding = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)

            if (useGridLayout) {
                // Untuk Medium/Expanded dalam landscape: tampilkan grid 2 card per baris
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifierWithPadding
                ) {
                    items(portfolioItems) { item ->
                        PortfolioCard(item)
                    }
                }
            } else {
                // Untuk device Compact, atau untuk Medium/Expanded dalam portrait (atau kondisi lainnya), gunakan list layout
                LazyColumn(modifier = modifierWithPadding) {
                    items(portfolioItems) { item ->
                        PortfolioCard(item)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun PortfolioCard(item: PortfolioItem) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "Project",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF549BC4),
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (expanded) item.description else item.description.take(50) + "...",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Teknologi: ${item.techStack.joinToString(", ")}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF549BC4)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (expanded) "Tampilkan lebih sedikit" else "Baca Selengkapnya",
                    fontSize = 15.sp,
                    color = Color(0xFF549BC4),
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        }
    }
}

// Data model portofolio
data class PortfolioItem(
    val title: String,
    val description: String,
    val techStack: List<String>,
    val image: Int
)

val portfolioItems = listOf(
    PortfolioItem(
        "Sistem Informasi Tingkat Akhir (SITA)",
        "Proyek Sistem Informasi TA yang kami kembangkan adalah sebuah platform berbasis website yang bertujuan untuk mendukung pengelolaan Tugas Akhir (TA) mahasiswa di Program Studi Informatika. Website ini memiliki beberapa jenis pengguna dengan akses dan fungsionalitas yang berbeda, yakni: mahasiswa tingkat akhir, dosen pembimbing, dosen penguji, dan koordinator TA.",
        listOf("Java Script", "PHP", "CSS"),
        R.drawable.portofolio_2
    ),
    PortfolioItem(
        "Sistem Informasi Konseling(SIMBA)",
        "Sistem Informasi Mahasiswa IT Del dirancang untuk mempermudah pengelolaan data pelanggaran, poin kebaikan, serta aktivitas konseling dan perwalian mahasiswa. Sistem ini bertujuan meningkatkan efisiensi dalam pengelolaan data mahasiswa, memfasilitasi komunikasi antara pihak kampus, mahasiswa, dan orang tua, serta mendukung pembinaan mahasiswa.",
        listOf("Java Script", "PHP", "CSS"),
        R.drawable.portofolio_1
    ),
    PortfolioItem(
        "Kedai Kopi Kenangan Senja",
        "Ini adalah proyek pribadi yang saya bangun yang bertujuan sebagai website dalam kedai kopi yang dapat melihat produk yang di jual serta memesan produk langsung dari website ini.",
        listOf("HTML", "Java Script", "CSS"),
        R.drawable.portofolio_3
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPortofolioScreen() {
    PortofoliokuTheme {
        PortofolioScreen(navController = rememberNavController())
    }
}
