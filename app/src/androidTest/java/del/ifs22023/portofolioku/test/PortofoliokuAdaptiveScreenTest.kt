package del.ifs22023.portofolioku.test

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import del.ifs22023.portofolioku.test.TestCompactWidth
import del.ifs22023.portofolioku.test.TestMediumWidth
import del.ifs22023.portofolioku.test.TestExpandedWidth
import del.ifs22023.portofolioku.ui.Beranda.BerandaAdaptiveScreen
import del.ifs22023.portofolioku.ui.kontak.KontakAdaptiveScreen
import del.ifs22023.portofolioku.ui.portofolio.PortofolioScreen
import del.ifs22023.portofolioku.ui.tentang.TentangSayaAdaptiveScreen
import org.junit.Rule
import org.junit.Test

class PortofoliokuAdaptiveScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Fungsi helper untuk override konfigurasi layar (screenWidthDp dan screenHeightDp)
     * guna mensimulasikan device resizable (API 34).
     */
    private fun setContentForResizable(
        widthDp: Int,
        heightDp: Int,
        content: @androidx.compose.runtime.Composable () -> Unit
    ) {
        composeTestRule.setContent {
            val configuration = Configuration(LocalConfiguration.current).apply {
                screenWidthDp = widthDp
                screenHeightDp = heightDp
            }
            CompositionLocalProvider(LocalConfiguration provides configuration) {
                content()
            }
        }
    }

    // ------------------------
    // Uji Layar Beranda
    // ------------------------

    @Test
    @TestCompactWidth
    fun compactDevice_berandaScreen_isDisplayed() {
        setContentForResizable(widthDp = 320, heightDp = 640) {
            BerandaAdaptiveScreen(
                navController = rememberNavController(),
                activity = composeTestRule.activity
            )
        }
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_berandaScreen_isDisplayed() {
        setContentForResizable(widthDp = 600, heightDp = 1024) {
            BerandaAdaptiveScreen(
                navController = rememberNavController(),
                activity = composeTestRule.activity
            )
        }
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_berandaScreen_isDisplayed() {
        setContentForResizable(widthDp = 840, heightDp = 1024) {
            BerandaAdaptiveScreen(
                navController = rememberNavController(),
                activity = composeTestRule.activity
            )
        }
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
    }

    // ------------------------
    // Uji Layar Tentang Saya
    // ------------------------

    @Test
    @TestCompactWidth
    fun compactDevice_tentangSayaScreen_isDisplayed() {
        setContentForResizable(widthDp = 320, heightDp = 640) {
            TentangSayaAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Tentang Saya").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kontak Saya").assertIsDisplayed()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_tentangSayaScreen_isDisplayed() {
        setContentForResizable(widthDp = 600, heightDp = 1024) {
            TentangSayaAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Tentang Saya").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kontak Saya").assertIsDisplayed()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_tentangSayaScreen_isDisplayed() {
        setContentForResizable(widthDp = 840, heightDp = 1024) {
            TentangSayaAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Tentang Saya").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yessi Sitanggang").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kontak Saya").assertIsDisplayed()
    }

    // ------------------------
    // Uji Layar Kontak
    // ------------------------

    @Test
    @TestCompactWidth
    fun compactDevice_kontakScreen_isDisplayed() {
        setContentForResizable(widthDp = 320, heightDp = 640) {
            KontakAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        // Pada tampilan compact, diharapkan teks "Hubungi Saya" tampil
        composeTestRule.onNodeWithText("Hubungi Saya").assertIsDisplayed()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_kontakScreen_isDisplayed() {
        setContentForResizable(widthDp = 600, heightDp = 1024) {
            KontakAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Hubungi Saya").assertIsDisplayed()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_kontakScreen_isDisplayed() {
        setContentForResizable(widthDp = 840, heightDp = 1024) {
            KontakAdaptiveScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Hubungi Saya").assertIsDisplayed()
    }

    // ------------------------
    // Uji Layar Portofolio
    // ------------------------

    @Test
    @TestCompactWidth
    fun compactDevice_portofolioScreen_isDisplayed() {
        setContentForResizable(widthDp = 320, heightDp = 640) {
            PortofolioScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        // Pastikan salah satu teks dari konten portofolio tampil, misalnya judul proyek
        composeTestRule.onNodeWithText("Sistem Informasi Tingkat Akhir (SITA)").assertIsDisplayed()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_portofolioScreen_isDisplayed() {
        setContentForResizable(widthDp = 600, heightDp = 1024) {
            PortofolioScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Sistem Informasi Tingkat Akhir (SITA)").assertIsDisplayed()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_portofolioScreen_isDisplayed() {
        setContentForResizable(widthDp = 840, heightDp = 1024) {
            PortofolioScreen(
                navController = rememberNavController(),
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Sistem Informasi Tingkat Akhir (SITA)").assertIsDisplayed()
    }
}
