package com.ghulam_18102194.praktikum14

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ghulam_18102194.praktikum14.model.Token
import com.ghulam_18102194.praktikum14.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tokenPref: TokenPref
    private lateinit var token: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_my_quotes, R.id.navigation_class_quotes, R.id.navigation_global_quotes))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        tokenPref = TokenPref(this)
        token = tokenPref.getToken()
        if (TextUtils.isEmpty(token.token)) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}