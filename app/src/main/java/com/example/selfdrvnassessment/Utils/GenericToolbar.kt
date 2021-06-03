package com.example.selfdrvnassessment.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.selfdrvnassessment.R

class GenericToolbar : ConstraintLayout {

    private lateinit var mainBackground: ConstraintLayout
    private lateinit var backButton: ImageView
    private lateinit var optionButton: ImageView
    private lateinit var llOptionButton: LinearLayout
    private lateinit var textTitle: TextView


    constructor(context: Context) : super(context) {
        init(context)
    }

    @SuppressLint("ResourceType")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.custom_generic_toolbar, this, true)
        textTitle = findViewById(R.id.tvToolbarTitle)
        backButton = findViewById(R.id.ivToolbarBackButton)

        llOptionButton = findViewById(R.id.llToolbarOptionButton)
        llOptionButton.visibility = View.GONE
    }

    fun changeToolbarBackground(color: Int) {
        mainBackground.setBackgroundColor(color)
    }

    fun setTitleCenter(){
        textTitle.gravity = Gravity.CENTER
    }

    fun setBackIconPressedListener(func: () -> Unit) {
        backButton.setOnClickListener {
            func.invoke()
        }
    }

    fun setBackIconVisibility(param: Int) {
        backButton.visibility = param
    }

//    fun setBackIconPressedListener(func:() -> Unit) {
//        backButton.setOnClickListener{
//            func
//        }
//    }

    fun setOptionPressedListener(func: () -> Unit){
        llOptionButton.setOnClickListener {
            func.invoke()
        }
    }

    fun setOptionButtonVisibility(param: Int) {
        llOptionButton.visibility = param
    }

    fun setOptionButtonImage(param: Int) {
        optionButton.setBackgroundResource(param)
    }

    fun setToolbarTitle(title: String) {
        textTitle.text = title
    }

    fun setToolbarTextColor(intColor:Int){
        textTitle.setTextColor(ContextCompat.getColor(context,intColor))
    }

    fun setToolbarPosition(position: Int) {
        textTitle.gravity = position
    }

}