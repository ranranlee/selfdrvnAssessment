package com.example.selfdrvnassessment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

abstract class BaseActivity:AppCompatActivity() {

    var width: Int = 0
    var height: Int = 0

    private var loadingDialog: AlertDialog? = null

    abstract fun getLayoutResourceId(): Int
    abstract fun onActivityCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        onActivityCreated()


        val display = windowManager.defaultDisplay
        height = display.height
        width = display.width


    }

    fun debugLog(message: String) {
        Log.d("debugMessage", message)
    }

    override fun onDestroy() {
        super.onDestroy()

    }




    fun showToast(msg: String) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show()
    }


     fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


     fun hideKeyBoard(et: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
    }

    fun displayLoadingDialog(){
        loadingDialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_dialog)
            .show()
        loadingDialog?.setCancelable(false)
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog?.show()
    }

    fun hideLoadingDialog(){
        if (loadingDialog!=null){
            loadingDialog?.dismiss()
        }

    }

}