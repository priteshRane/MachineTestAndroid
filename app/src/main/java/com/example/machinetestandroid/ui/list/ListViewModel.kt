package com.example.machinetestandroid.ui.list

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.machinetestandroid.data.repositories.AnswerRepository
import com.example.machinetestandroid.util.Coroutines
import com.ransoft.androidpaging.util.NoInternetException
import com.ransoft.androidpaging.util.toast
import java.lang.Exception

class ListViewModel(val context: Context, val answerRepository: AnswerRepository) : ViewModel() {
    val TAG = "ListViewModel"

    fun getAnswers() {
        Coroutines.main {
            try {
                val answerResponse = answerRepository.answerData(1, 10, "desc", "activity", "stackoverflow")
                if (answerResponse.isSuccessful) {
                    answerResponse.body()?.items
                }
            } catch (e: NoInternetException) {
                Log.i(TAG, e.toString())
                context.toast("No Internet, Please check your connection")
            } catch (e: Exception) {
                Log.i(TAG, e.toString())
                context.toast("Something went wrong, Please try again!")
            }
        }
    }
}
