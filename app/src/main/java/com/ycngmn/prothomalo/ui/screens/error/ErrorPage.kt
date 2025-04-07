package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.TextStyles


@Composable
fun ErrorPage (
    imageId: Int,
    errorText: String,
    errorDescription: String,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(imageId),
            contentDescription = null,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        )

        Spacer(Modifier.height(5.dp))

        Text (
            text = errorText,
            fontSize = FontSizes.errorTitle,
            style = TextStyles.errorTitle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = errorDescription,
            fontSize = FontSizes.errorSub,
            style = TextStyles.errorSub,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 15.dp)

        )

        if (errorDescription.isNotEmpty()) Spacer(Modifier.height(25.dp))
        else Spacer(Modifier.height(10.dp))

        Button(
            border = BorderStroke(color = PaloBlue, width = 2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
            onClick = { onButtonClick() },
        ) { 
            Text(buttonText,
                fontSize = FontSizes.errorSub,
                style = TextStyles.errorSub,
                color = MaterialTheme.colorScheme.onBackground)
        }
    }
}