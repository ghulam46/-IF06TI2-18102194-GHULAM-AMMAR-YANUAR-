package com.ghulam_18102194.praktikum14.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token (
        var token: String? = null
): Parcelable