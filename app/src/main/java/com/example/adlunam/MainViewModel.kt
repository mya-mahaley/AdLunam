package com.example.adlunam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class MainViewModel : ViewModel() {
    private var displayName = MutableLiveData("Uninitialized")
    private var email = MutableLiveData("Uninitialized")
    private var uid = MutableLiveData("Uninitialized")
    private var signedIn = MutableLiveData(true)
    private var triviaScore = MutableLiveData(Pair(0.0,0.0))

    private fun userLogout() {
        displayName.postValue("No user")
        email.postValue("No email, no active user")
        uid.postValue("No uid, no active user")
    }

    fun updateUser() {
        // XXX Write me. Update user data in view model
        val user = FirebaseAuth.getInstance().currentUser
        email.postValue(user.email)
        uid.postValue(user.uid)
        displayName.postValue(user.displayName)
    }

    fun setSignedIn(signed: Boolean){
        signedIn.value = signed
    }

    fun setTriviaScore(score: Pair<Double, Double>){
        triviaScore.value = score
    }

    fun correctAnswer() {
        val correct = triviaScore.value!!.first.plus(1.0)
        val total = triviaScore.value!!.second.plus(1.0)
        triviaScore.value = Pair(correct, total)
    }

    fun incorrectAnswer() {
        val correct = triviaScore.value!!.first
        val total = triviaScore.value!!.second.plus(1.0)
        triviaScore.value = Pair(correct, total)
    }

    fun resetScore() {
        triviaScore.value = Pair(0.0,0.0)
    }


    fun observeTriviaScore() : LiveData<Pair<Double, Double>> {
        return triviaScore
    }



    fun observeDisplayName() : LiveData<String> {
        return displayName
    }
    fun observeEmail() : LiveData<String> {
        return email
    }
    fun observeUid() : LiveData<String> {
        return uid
    }
    fun observerSignedIn() : LiveData<Boolean> {
        return signedIn
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
        setSignedIn(false)
    }
}