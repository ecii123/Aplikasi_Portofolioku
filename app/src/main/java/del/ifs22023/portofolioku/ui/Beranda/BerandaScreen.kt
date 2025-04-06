package del.ifs22023.portofolioku.ui.Beranda
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import del.ifs22023.portofolioku.R
import del.ifs22023.portofolioku.ui.theme.PortofoliokuTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import java.io.File

/**
 * Fungsi untuk membuka file PDF dari assets.
 */
fun openPdfFromAssets(context: Context, fileName: String) {
    val assetManager = context.assets
    val file = File(context.cacheDir, fileName)

    // Salin file dari assets ke cache
    assetManager.open(fileName).use { inputStream ->
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    // Dapatkan URI file menggunakan FileProvider
    val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(intent)
}

/**
 * Layout untuk tampilan Compact (single-column).
 */
/**
 * Layout untuk tampilan Compact (single-column).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreenCompact(navController: NavController, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Layout untuk phone dalam orientasi landscape:
        // Tidak ada TopAppBar, gambar di atas, dan keterangan di bawahnya.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Elemen gambar utama di bagian atas
            Image(
                painter = painterResource(id = R.drawable.foto_diri),
                contentDescription = "Foto Diri",
//                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
//                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Elemen teks dan tombol (tanpa gambar tambahan)
            HeaderSectionLandscape(navController)
        }
    } else {
        // Layout untuk orientasi portrait (seperti sebelumnya dengan TopAppBar)
        Scaffold(
            topBar = {
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
            },
            content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    item {
                        HeaderSection(navController = navController)
                    }
                }
            }
        )
    }
}

@Composable
fun HeaderSectionLandscape(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Halo semua !!! saya,",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF549BC4)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Yessi Sitanggang",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Front-End Developer",
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Saya, mahasiswa aktif Institut Teknologi Del, tengah mempersiapkan diri untuk berkompetisi di bidang IT.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(end = 22.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                openPdfFromAssets(navController.context, "Yessi_Sitanggang_CV.pdf")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
            modifier = Modifier
                .width(150.dp)
                .height(60.dp)
        ) {
            Text(text = "Lihat CV", color = Color.White, fontSize = 17.7.sp)
        }
    }
}


/**
 * Layout untuk tampilan Medium/Expanded (two-column).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreenMediumExpanded(navController: NavController, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    // Anggap device tablet jika smallestScreenWidthDp >= 600
    val isTablet = configuration.smallestScreenWidthDp >= 600

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Batasi lebar konten agar tidak terlalu melebar
            Box(
                modifier = Modifier
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(24.dp)
            ) {
                if (isTablet) {
                    // Hanya untuk tablet: atur layout berdasarkan orientasi dengan scroll
                    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        // Layout Tablet Portrait dengan scrolling vertikal
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.foto_diri),
                                    contentDescription = "Foto Diri",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                            // Bagian bawah: Teks dan Tombol
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Halo semua !!! saya,",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = Color(0xFF549BC4)
                                )
                                Text(
                                    text = "Yessi Sitanggang",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Front-End Developer",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "Saya, mahasiswa aktif Institut Teknologi Del, tengah mempersiapkan diri untuk berkompetisi di bidang IT.",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Button(
                                    onClick = {
                                        openPdfFromAssets(navController.context, "Yessi_Sitanggang_CV.pdf")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
                                    modifier = Modifier
                                        .width(180.dp)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = "Lihat CV",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    } else {
                        // Layout Tablet Landscape dengan scrolling vertikal jika diperlukan
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Kolom kiri: Gambar
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.foto_diri),
                                    contentDescription = "Foto Diri",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(350.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                            // Kolom kanan: Teks & Tombol
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Halo semua !!! saya,",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = Color(0xFF549BC4)
                                )
                                Text(
                                    text = "Yessi Sitanggang",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Front-End Developer",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "Saya, mahasiswa aktif Institut Teknologi Del, tengah mempersiapkan diri untuk berkompetisi di bidang IT.",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                )
                                Button(
                                    onClick = {
                                        openPdfFromAssets(navController.context, "Yessi_Sitanggang_CV.pdf")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
                                    modifier = Modifier
                                        .width(180.dp)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = "Lihat CV",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Untuk device non-tablet, panggil layout phone yang sudah didefinisikan
                    BerandaScreenCompact(navController)
                }
            }
        }
    }
}


/**
 * Jika HeaderSection juga digunakan di tampilan lain (misalnya untuk Compact),
 * berikut adalah versi aslinya.
 */
@Composable
fun HeaderSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Padding horizontal untuk seluruh kolom
    ) {
        Text(
            text = "Halo semua !!! saya,",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF549BC4)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Yessi Sitanggang",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Front-End Developer",
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Saya, mahasiswa aktif Institut Teknologi Del, tengah mempersiapkan diri untuk berkompetisi di bidang IT.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(end = 22.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                openPdfFromAssets(navController.context, "Yessi_Sitanggang_CV.pdf")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
            modifier = Modifier
                .width(150.dp)
                .height(60.dp)
        ) {
            Text(text = "Lihat CV", color = Color.White, fontSize = 17.7.sp)
        }
        Spacer(modifier = Modifier.height(35.dp))
        Image(
            painter = painterResource(id = R.drawable.foto_diri),
            contentDescription = "Deskripsi gambar",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}


/**
 * Komposable adaptif yang memilih layout Compact atau Medium/Expanded
 * berdasarkan ukuran layar.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun BerandaAdaptiveScreen(navController: NavController, activity: Activity) {
    val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity)
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            BerandaScreenCompact(navController)
        }
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> {
            BerandaScreenMediumExpanded(navController)
        }
        else -> {
            BerandaScreenCompact(navController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBerandaScreenCompact() {
    PortofoliokuTheme {
        BerandaScreenCompact(navController = rememberNavController())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBerandaScreenMediumExpanded() {
    PortofoliokuTheme {
        BerandaScreenMediumExpanded(navController = rememberNavController())
    }
}