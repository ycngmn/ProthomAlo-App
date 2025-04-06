package com.ycngmn.prothomalo.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import com.ycngmn.prothomalo.ui.theme.PaloBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubButtons(isDarkTheme: Boolean, settingsVM: SettingsVM, navController : NavController) {

    val subPairs = listOf(
        Pair(if (isDarkTheme) R.drawable.palo_bangla_night else R.drawable.palo_bangla_logo, PaloKeys.PaloMain),
        Pair(if (isDarkTheme) R.drawable.palo_eng_night else R.drawable.english_site_og_image, PaloKeys.PaloEnglish),
        Pair(if (isDarkTheme) R.drawable.kishore_alo_night else R.drawable.kishore_alo_logo, PaloKeys.KishorAlo),
        Pair(R.drawable.muktijuddho_logo, PaloKeys.Mukti1971),
        Pair(R.drawable.biggan_chinta_logo, PaloKeys.BigganChinta),
        Pair(R.drawable.nagorik_songbad_logo, PaloKeys.Nagorik),
        Pair(if (isDarkTheme) R.drawable.bondhushava_night else R.drawable.bondhushava_logo, PaloKeys.BondhuShava),
        Pair(if (isDarkTheme) R.drawable.palo_trust_night else R.drawable.prothomalo_trust_logo, PaloKeys.PaloTrust)
    )

    FlowRow (modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        subPairs.forEach  { (imageRes, key) ->

            val isCurrent = settingsVM.paloKey.collectAsState().value == key
            Button(
                modifier = Modifier.height(62.dp).width(160.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                border = BorderStroke(1.dp, if (isCurrent) PaloBlue else Color.Gray),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (!isCurrent) {
                        settingsVM.setPaloKey(key)
                        PaloGlobal.paloKey = key
                        navController.navigate("home") {
                            launchSingleTop = true
                        }
                    }
                }
            ) { Image(painter = painterResource(id = imageRes), contentDescription = null) }
        }
    }
}