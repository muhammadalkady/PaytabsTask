package kady.muhammad.paytabstask.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kady.muhammad.paytabstask.R
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersScreen
import kady.muhammad.paytabstask.presentation.ui.theme.Black
import kady.muhammad.paytabstask.presentation.ui.theme.PaytabsTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect { setSystemBarColors(systemUiController) }
            PaytabsTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(topBar = { AppTopBar() }) {
                        CharactersScreen()
                    }
                }
            }
        }
    }

    private fun setSystemBarColors(systemUiController: SystemUiController) {
        systemUiController.setSystemBarsColor(
            color = Black,
            darkIcons = false
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun AppTopBar() {
    TopAppBar(backgroundColor = Color.Black) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_marvel_logo),
            contentDescription = null, alignment = Alignment.Center
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaytabsTaskTheme {
        CharactersScreen()
    }
}