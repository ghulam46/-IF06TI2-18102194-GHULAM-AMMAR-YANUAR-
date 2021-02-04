package com.ghulam_18102194.praktikum14.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ghulam_18102194.praktikum14.CoroutineContextProvider
import com.ghulam_18102194.praktikum14.MainActivity
import com.ghulam_18102194.praktikum14.R
import com.ghulam_18102194.praktikum14.TokenPref
import com.ghulam_18102194.praktikum14.`interface`.MainView
import com.ghulam_18102194.praktikum14.api.MainPresenter
import com.ghulam_18102194.praktikum14.databinding.ActivityLoginBinding
import com.ghulam_18102194.praktikum14.model.Login
import com.ghulam_18102194.praktikum14.model.Quote
import com.ghulam_18102194.praktikum14.model.Token


class LoginActivity : AppCompatActivity(), View.OnClickListener, MainView {
        private lateinit var presenter: MainPresenter
        private lateinit var binding: ActivityLoginBinding
        private lateinit var tokenPref: TokenPref
        private lateinit var token: Token
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.btnSign.setOnClickListener(this)
            presenter = MainPresenter(this, CoroutineContextProvider())
            tokenPref = TokenPref(this)
            token = tokenPref.getToken()
        }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSign -> {
                presenter.login(
                        binding.inputNim.text.toString(),
                        binding.inputPassword.text.toString()
                )
            }
        }
    }

    override fun showMessage(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_SHORT).show()
    }
    override fun resultQuote(data: ArrayList<Quote>) {
    }
    override fun resultLogin(data: Login) {
        if (!TextUtils.isEmpty(data.token)) {
            token.token = data.token
            tokenPref.setToken(token)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
