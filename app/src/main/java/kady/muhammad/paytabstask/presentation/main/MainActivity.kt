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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kady.muhammad.paytabstask.R
import kady.muhammad.paytabstask.presentation.characters_screen.CharactersScreen
import kady.muhammad.paytabstask.presentation.ui.theme.Black
import kady.muhammad.paytabstask.presentation.ui.theme.PaytabsTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Black,
                    darkIcons = false
                )
            }
            PaytabsTaskTheme {
                // A surface container using the 'background' color from the theme
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

}

@Composable
private fun AppTopBar() {
    TopAppBar {
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