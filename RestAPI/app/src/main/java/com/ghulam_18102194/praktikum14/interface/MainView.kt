package com.ghulam_18102194.praktikum14.`interface`

import com.ghulam_18102194.praktikum14.model.Login
import com.ghulam_18102194.praktikum14.model.Quote

interface MainView {
    fun showMessage(messsage : String)
    fun resultQuote(data: ArrayList<Quote>)
    fun resultLogin(data: Login)
}