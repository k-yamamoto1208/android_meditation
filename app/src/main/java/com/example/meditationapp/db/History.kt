package com.example.meditationapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String?,
    @ColumnInfo(name = "meditation_time") val meditationTime: String?,
    val music: String?,
    val memo: String?
): Serializable
