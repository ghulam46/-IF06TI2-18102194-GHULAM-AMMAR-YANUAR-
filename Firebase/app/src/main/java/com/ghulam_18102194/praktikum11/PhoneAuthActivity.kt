package com.ghulam_18102194.praktikum11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ghulam_18102194.praktikum11.databinding.ActivityPhoneAuthBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPhoneAuthBinding
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private var prefixPhoneNumber: String? = "+62"
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks:
            PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnVerify.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.btnResend.setOnClickListener(this)
        auth = Firebase.auth

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verificationInProgress = false
                signInWithPhoneAuthCredential(credential)
            }
            override fun onVerificationFailed(e: FirebaseException) {
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    binding.inputNumber.error = "Invalid phone number."
                } else if (e is FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show()
                }
            }
            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            binding.inputVerifyCode.error = "Invalid code."
                        }
                    }
                }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnVerify -> {
                val phoneNumber = binding.inputNumber.text.toString()
                if (TextUtils.isEmpty(phoneNumber)) {
                    binding.inputNumber.error = "Invalid phone number."
                    return
                }

                startPhoneNumberVerification(prefixPhoneNumber+binding.inputNumber.text.toString
                ())
            }
            R.id.btnContinue -> {
                val code = binding.inputVerifyCode.text.toString()
                if (TextUtils.isEmpty(code)) {
                    binding.inputVerifyCode.error = "Cannot be empty."
                    return
                }
                verifyPhoneNumberWithCode(storedVerificationId, code)
            }
            R.id.btnResend ->
                resendVerificationCode(prefixPhoneNumber+binding.inputNumber.text.toString(),
                        resendToken)
        }
    }
    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        verificationInProgress = true
    }
    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
            phoneNumber: String,
            token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
        if (token != null) {
            optionsBuilder.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

}