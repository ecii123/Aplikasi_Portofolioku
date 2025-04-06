package del.ifs22023.portofolioku.ui.kontak

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
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

// ====================
// Layout Compact (untuk phone)
// ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontakScreenCompact(navController: NavController, modifier: Modifier = Modifier) {
    // State lokal untuk form
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nomor by remember { mutableStateOf("") }
    var pesan by remember { mutableStateOf("") }
    var formSubmitted by remember { mutableStateOf(false) }

    var namaError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var nomorError by remember { mutableStateOf<String?>(null) }
    var pesanError by remember { mutableStateOf<String?>(null) }

    val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validateForm(): Boolean {
        namaError = if (nama.isEmpty()) "Nama tidak boleh kosong" else null
        emailError = when {
            email.isEmpty() -> "Email tidak boleh kosong"
            !EMAIL_REGEX.matches(email) -> "Format email tidak valid"
            else -> null
        }
        nomorError = when {
            nomor.isEmpty() -> "Nomor telepon tidak boleh kosong"
            !nomor.matches(Regex("^\\+?[0-9]{10,}$")) -> "Nomor tidak valid"
            else -> null
        }
        pesanError = if (pesan.isEmpty()) "Pesan tidak boleh kosong" else null
        return listOf(namaError, emailError, nomorError, pesanError).all { it == null }
    }

    LaunchedEffect(formSubmitted) {
        if (formSubmitted) {
            println("Form berhasil dikirim")
            formSubmitted = false
        }
    }

    // Layout Compact: selalu menggunakan lebar 350dp, satu kolom (baik portrait maupun landscape)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
//        // Background
//        Image(
//            painter = painterResource(id = R.drawable.frame_5),
//            contentDescription = "Background",
//            modifier = Modifier
//                .fillMaxSize()
//                .offset(y = -200.dp),
//            contentScale = ContentScale.Crop
//        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 108.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Hubungi Saya",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color(0xFF549BC4),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = nama,
                onValueChange = {
                    nama = it
                    validateForm()
                },
                label = { Text("Nama") },
                isError = namaError != null,
                supportingText = { namaError?.let { ErrorText(it) } },
                modifier = Modifier
                    .width(350.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    validateForm()
                },
                label = { Text("Email") },
                isError = emailError != null,
                supportingText = { emailError?.let { ErrorText(it) } },
                modifier = Modifier
                    .width(350.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            OutlinedTextField(
                value = nomor,
                onValueChange = {
                    nomor = it
                    validateForm()
                },
                label = { Text("Nomor Telepon") },
                isError = nomorError != null,
                supportingText = { nomorError?.let { ErrorText(it) } },
                modifier = Modifier
                    .width(350.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            OutlinedTextField(
                value = pesan,
                onValueChange = {
                    pesan = it
                    validateForm()
                },
                label = { Text("Pesan") },
                isError = pesanError != null,
                supportingText = { pesanError?.let { ErrorText(it) } },
                modifier = Modifier
                    .width(350.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Button(
                onClick = {
                    if (validateForm()) {
                        formSubmitted = true
                        nama = ""
                        email = ""
                        nomor = ""
                        pesan = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
                modifier = Modifier
                    .width(160.dp)
                    .height(50.dp)
            ) {
                Text("Kirim", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

// ====================
// Layout Medium/Expanded
// ====================
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun KontakScreenMediumExpanded(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val widthSizeClass = calculateWindowSizeClass(activity = context as androidx.activity.ComponentActivity).widthSizeClass

    // Untuk Medium/Expanded:
    // Bila portrait, gunakan layout Compact (sama seperti pada KontakScreenCompact)
    // Bila landscape, gunakan layout Enhanced: elemen diperbesar dan dengan padding vertikal lebih tinggi.
    val enhanced = (widthSizeClass != WindowWidthSizeClass.Compact) && isLandscape

    // State lokal untuk form (menggunakan state lokal yang sama)
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nomor by remember { mutableStateOf("") }
    var pesan by remember { mutableStateOf("") }
    var formSubmitted by remember { mutableStateOf(false) }

    var namaError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var nomorError by remember { mutableStateOf<String?>(null) }
    var pesanError by remember { mutableStateOf<String?>(null) }

    val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validateForm(): Boolean {
        namaError = if (nama.isEmpty()) "Nama tidak boleh kosong" else null
        emailError = when {
            email.isEmpty() -> "Email tidak boleh kosong"
            !EMAIL_REGEX.matches(email) -> "Format email tidak valid"
            else -> null
        }
        nomorError = when {
            nomor.isEmpty() -> "Nomor telepon tidak boleh kosong"
            !nomor.matches(Regex("^\\+?[0-9]{10,}$")) -> "Nomor tidak valid"
            else -> null
        }
        pesanError = if (pesan.isEmpty()) "Pesan tidak boleh kosong" else null
        return listOf(namaError, emailError, nomorError, pesanError).all { it == null }
    }

    LaunchedEffect(formSubmitted) {
        if (formSubmitted) {
            println("Form berhasil dikirim")
            formSubmitted = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
//        // Background
//        Image(
//            painter = painterResource(id = R.draw
        //            able.frame_5),
//            contentDescription = "Background",
//            modifier = Modifier
//                .fillMaxSize()
//            .offset(y = -380.dp),
//            contentScale = ContentScale.Crop
//        )
        if (enhanced) {
            // Layout Enhanced untuk Medium/Expanded dalam mode Landscape
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Hubungi Saya",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color(0xFF549BC4),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                        validateForm()
                    },
                    label = { Text("Nama") },
                    isError = namaError != null,
                    supportingText = { namaError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(400.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        validateForm()
                    },
                    label = { Text("Email") },
                    isError = emailError != null,
                    supportingText = { emailError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(400.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = nomor,
                    onValueChange = {
                        nomor = it
                        validateForm()
                    },
                    label = { Text("Nomor Telepon") },
                    isError = nomorError != null,
                    supportingText = { nomorError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(400.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = pesan,
                    onValueChange = {
                        pesan = it
                        validateForm()
                    },
                    label = { Text("Pesan") },
                    isError = pesanError != null,
                    supportingText = { pesanError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(400.dp)  // Sama lebar dengan field lainnya
                        .height(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Button(
                    onClick = {
                        if (validateForm()) {
                            formSubmitted = true
                            nama = ""
                            email = ""
                            nomor = ""
                            pesan = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
                    modifier = Modifier
                        .width(180.dp)
                        .height(55.dp)
                ) {
                    Text("Kirim", color = Color.White, fontSize = 20.sp)
                }
            }
        } else {
            // Layout untuk Medium/Expanded dalam mode Portrait (sama seperti Compact)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 108.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Hubungi Saya",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF549BC4),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                        validateForm()
                    },
                    label = { Text("Nama") },
                    isError = namaError != null,
                    supportingText = { namaError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(350.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        validateForm()
                    },
                    label = { Text("Email") },
                    isError = emailError != null,
                    supportingText = { emailError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(350.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = nomor,
                    onValueChange = {
                        nomor = it
                        validateForm()
                    },
                    label = { Text("Nomor Telepon") },
                    isError = nomorError != null,
                    supportingText = { nomorError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(350.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                OutlinedTextField(
                    value = pesan,
                    onValueChange = {
                        pesan = it
                        validateForm()
                    },
                    label = { Text("Pesan") },
                    isError = pesanError != null,
                    supportingText = { pesanError?.let { ErrorText(it) } },
                    modifier = Modifier
                        .width(350.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Button(
                    onClick = {
                        if (validateForm()) {
                            formSubmitted = true
                            nama = ""
                            email = ""
                            nomor = ""
                            pesan = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF549BC4)),
                    modifier = Modifier
                        .width(160.dp)
                        .height(50.dp)
                ) {
                    Text("Kirim", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

// ======================================
// Fungsi ErrorText
// ======================================
@Composable
private fun ErrorText(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
}

// ======================================
// Adaptive Kontak Screen: pilih Compact atau Medium/Expanded
// ======================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontakAdaptiveScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val widthSizeClass = when {
        screenWidth < 600 -> WindowWidthSizeClass.Compact
        screenWidth < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }

    if (widthSizeClass == WindowWidthSizeClass.Compact) {
        KontakScreenCompact(navController, modifier)
    } else {
        KontakScreenMediumExpanded(navController, modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewKontakScreen() {
    PortofoliokuTheme {
        KontakAdaptiveScreen(navController = rememberNavController())
    }
}
