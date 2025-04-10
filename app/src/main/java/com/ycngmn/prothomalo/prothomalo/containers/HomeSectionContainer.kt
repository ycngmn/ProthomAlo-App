package com.ycngmn.prothomalo.prothomalo.containers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys

@Entity(tableName = "home_sections")
data class HomeSectionContainer(
    @PrimaryKey val paloKey: PaloKeys,
    val homeSections: Map<String, String>
)