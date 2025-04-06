package del.ifs22023.portofolioku.ui.tentang

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import del.ifs22023.portofolioku.R
import del.ifs22023.portofolioku.ui.theme.PortofoliokuTheme

// Tampilan default untuk perangkat compact (ponsel)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TentangSayaScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Tentang Saya",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF549BC4),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Gambar profil
                Image(
                    painter = painterResource(R.drawable.tentang_3),
                    contentDescription = "Foto Profil",
//                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                // Nama
                Text(
                    text = "Yessi Sitanggang",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Deskripsi
                Text(
                    text = "Sebagai mahasiswa aktif semester enam jurusan Informatika di Institut Teknologi Del, " +
                            "saya memiliki satu tahun pengalaman sebagai Frontend Developer, bidang yang saya tekuni dan terus kembangkan hingga saat ini. " +
                            "Saya telah mengerjakan beberapa proyek sebagaimana tercantum dalam portofolio saya.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Tombol Kontak Saya
                Button(
                    onClick = {
                        navController.navigate(Routes.KONTAK)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF549BC4)
                    ),
                    modifier = Modifier
                        .width(160.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Kontak Saya",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}

// Tampilan khusus untuk mode Medium/Expanded (adaptive) dengan penyesuaian orientasi
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TentangSayaMediumExpanded(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val widthSizeClass = calculateWindowSizeClass(activity = context as androidx.activity.ComponentActivity).widthSizeClass

    // Jika perangkat tidak compact dan orientasi landscape, gunakan layout enhanced (Row)
    val enhanced = (widthSizeClass != WindowWidthSizeClass.Compact) && isLandscape

    Scaffold(
        content = { innerPadding ->
            if (enhanced) {
                // Layout untuk landscape: gambar di kiri, informasi di kanan
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gambar profil
                    Image(
                        painter = painterResource(R.drawable.tentang_3),
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                    )
                    // Kolom informasi
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Tentang Saya",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = Color(0xFF549BC4)
                        )
                        Text(
                            text = "Yessi Sitanggang",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Sebagai mahasiswa aktif semester enam jurusan Informatika di Institut Teknologi Del, " +
                                    "saya memiliki satu tahun pengalaman sebagai Frontend Developer, bidang yang saya tekuni dan terus kembangkan hingga saat ini. " +
                                    "Saya telah mengerjakan beberapa proyek sebagaimana tercantum dalam portofolio saya.",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start
                        )
                        Button(
                            onClick = {
                                navController.navigate(Routes.KONTAK)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF549BC4)
                            ),
                            modifier = Modifier
                                .width(180.dp)
                                .height(50.dp)
                        ) {
                            Text(
                                text = "Kontak Saya",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            } else {
                // Layout untuk portrait pada mode Medium/Expanded: tampilannya mirip dengan compact tapi dengan ukuran yang lebih besar
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Tentang Saya",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color(0xFF549BC4),
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(R.drawable.tentang_3),
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Yessi Sitanggang",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Sebagai mahasiswa aktif semester enam jurusan Informatika di Institut Teknologi Del, " +
                                "saya memiliki satu tahun pengalaman sebagai Frontend Developer, bidang yang saya tekuni dan terus kembangkan hingga saat ini. " +
                                "Saya telah mengerjakan beberapa proyek sebagaimana tercantum dalam portofolio saya.",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            navController.navigate(Routes.KONTAK)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF549BC4)
                        ),
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Kontak Saya",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    )
}

// Adaptive screen: pilih antara Compact atau Medium/Expanded berdasarkan lebar layar
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TentangSayaAdaptiveScreen(navController: NavController, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val widthSizeClass = when {
        screenWidth < 600 -> WindowWidthSizeClass.Compact
        screenWidth < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }

    if (widthSizeClass == WindowWidthSizeClass.Compact) {
        TentangSayaScreen(navController, modifier)
    } else {
        TentangSayaMediumExpanded(navController, modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTentangSayaAdaptiveScreen() {
    PortofoliokuTheme {
        TentangSayaAdaptiveScreen(navController = rememberNavController())
    }
}
