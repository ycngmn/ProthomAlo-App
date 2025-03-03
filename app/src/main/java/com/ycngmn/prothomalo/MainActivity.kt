package com.ycngmn.prothomalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ycngmn.prothomalo.utils.MainNavGraph


class NewsViewModel : ViewModel() {
    private var section = ""
    var newsUrls by mutableStateOf<List<String>>(emptyList())

    fun updateUrls(newUrls: List<String>) {
        newsUrls = newUrls
    }
    fun setSection(newSection: String) {
        section = newSection
    }
    fun getSection(): String {
        return section
    }
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavGraph()
        }
    }
}




