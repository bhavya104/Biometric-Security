package com.example.biometricsecurity

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast


class FingerPrintHelper(private val context: Context) :  FingerprintManager.AuthenticationCallback(){

    lateinit var cancellationSignal : CancellationSignal

    fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject){
        cancellationSignal = CancellationSignal()
        if(ActivityCompat.checkSelfPermission(context,Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED) {
            return
        }
        manager.authenticate(cryptoObject,cancellationSignal,0,this,null)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        Toast.makeText(context,"Authentication Error",Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        Toast.makeText(context,"Authentication Success",Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context,WelcomeActivity::class.java))
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        Toast.makeText(context,"Finger cannot be detected.Try Again!",Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Toast.makeText(context,"Authentication Failed",Toast.LENGTH_SHORT).show()
    }
}