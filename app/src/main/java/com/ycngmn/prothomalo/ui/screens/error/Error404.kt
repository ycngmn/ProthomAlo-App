package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R

@Composable
fun Error404(navController: NavController) {

    val errrorDesc = "আপনি যা খুঁজছেন, তা পাওয়া যায়নি। বিষয়টি হয়তো প্রথম আলোর অন্তর্ভুক্ত নয়" +
            ", অথবা অনুসন্ধানে কোনো ভুল হয়েছে। দয়া করে তথ্যটি নিশ্চিত করে আবার চেষ্টা করুন।"

    ErrorPage(R.drawable.error_404, "কিছু পাওয়া যায়নি", errrorDesc, "পিছনে ফিরে যান") {
        navController.navigateUp()
    }
}