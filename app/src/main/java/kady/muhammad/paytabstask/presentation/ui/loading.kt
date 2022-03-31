package kady.muhammad.paytabstask.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.*
import kady.muhammad.paytabstask.R

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.batman))
    LottieAnimation(composition, iterations = Int.MAX_VALUE)
}