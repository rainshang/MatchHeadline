package com.xyx.matchheadline.vo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class FeedsResponse(
    @SerializedName("items")
    val items: List<Feed>,
    @SerializedName("product")
    val product: String,
    @SerializedName("resultSize")
    val resultSize: Int,
    @SerializedName("version")
    val version: Int
) {
    @Parcelize
    data class Feed(
        @SerializedName("correctAnswerIndex")
        val correctAnswerIndex: Int,
        @SerializedName("headlines")
        val headlines: List<String>,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("section")
        val section: String,
        @SerializedName("standFirst")
        val standFirst: String,
        @SerializedName("storyUrl")
        val storyUrl: String
    ) : Parcelable
}