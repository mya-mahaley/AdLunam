package com.example.adlunam

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

// https://firebase.google.com/docs/auth/android/firebaseui
class AuthInit(viewModel: MainViewModel, signInLauncher: ActivityResultLauncher<Intent>) {
    companion object {
        private const val TAG = "AuthInit"
        fun setDisplayName(displayName : String, viewModel: MainViewModel) {
            Log.d(TAG, "XXX profile change request")
            // XXX Write me. User is attempting to update display name. Get the profile updates (see android doc)
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName)
            user.updateProfile(profileUpdates.build())
            viewModel.updateUser()
        }
    }

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null) {
            Log.d(TAG, "XXX user null")
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build())

            // Create and launch sign-in intent
            // XXX Write me. Set authentication providers and start sign-in for user
            val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginTheme)
                .setLogo(R.drawable.full_moon)
                .build()
            signInLauncher.launch(intent)

            viewModel.setSignedIn(true)

        } else {
            Log.d(TAG, "XXX user ${user.displayName} email ${user.email}")
            viewModel.updateUser()
            viewModel.setSignedIn(true)
        }
    }
}
