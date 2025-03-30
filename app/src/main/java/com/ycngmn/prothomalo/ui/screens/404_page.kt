package com.ycngmn.prothomalo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue


@Composable
fun ErrorPage (navController: NavController) {
    Column (Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(R.drawable.palo_404),
            contentDescription = "404 Error",
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        )
        Text (
            text = "কিছু পাওয়া যায়নি",
            fontFamily = ShurjoFamily,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "আপনি যা খুঁজছেন, তা পাওয়া যায়নি। বিষয়টি হয়তো প্রথম আলোর অন্তর্ভুক্ত নয়, অথবা অনুসন্ধানে কোনো ভুল হয়েছে। দয়া করে তথ্যটি নিশ্চিত করে আবার চেষ্টা করুন।",
            textAlign = TextAlign.Center,
            fontFamily = ShurjoFamily,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 15.dp)

        )

        Spacer(Modifier.height(25.dp))

        Button(
            border = BorderStroke(color = PaloBlue, width = 2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
            onClick = { navController.navigateUp() },
        ) { 
            Text("পিছনে ফিরে যান",textAlign = TextAlign.Center,
                fontFamily = ShurjoFamily,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground)
        }
    }
}