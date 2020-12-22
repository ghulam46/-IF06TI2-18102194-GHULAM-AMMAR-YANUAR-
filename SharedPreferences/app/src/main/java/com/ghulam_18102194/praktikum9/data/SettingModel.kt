package com.ghulam_18102194.praktikum9.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class SettingModel {
    @Parcelize
    data class SettingModel (
        var name: String? = null,
        var email: String? = null,
        var age: Int = 0,
        var phoneNumber: String? = null,
        var isDarkTheme: Boolean = false
    ): Parcelable
}