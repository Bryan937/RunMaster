package com.example.runmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.runmaster.components.splash_screen.SplashScreenLogo
import com.example.runmaster.components.splash_screen.TeamMemberList
import com.example.runmaster.constants.CommonConstants.MEDIUM_PADDING
import com.example.runmaster.constants.CommonConstants.SPACER_SIZE

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            SplashScreenLogo()
            Spacer(modifier = Modifier.size(SPACER_SIZE))
            TeamMemberList()
        }
    }

}
@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashScreen()
}