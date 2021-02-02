package com.ghulam_18102194.praktikum14.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quote(
    @SerializedName("quote_id")
    var quote_id: String? = null,
    @SerializedName("user_name")
    var user_name: String? = null,
    @SerializedName("class_name")
    var class_name: String? = null,
    @SerializedName("quote_name")
    var quote_name: String? = null,
    @SerializedName("quote_description")
    var quote_description: String? = null,
    @SerializedName("created_at")
    var created_at: String? = null
) : Parcelable