package com.example.selfdrvnassessment.activity

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.selfdrvnassessment.BaseActivity
import com.example.selfdrvnassessment.R
import com.example.selfdrvnassessment.model.AvengersDetailsModel
import com.example.selfdrvnassessment.roomDatabase.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.row_avenger_list.view.*

class DetailsActivity:BaseActivity() {
    var star1 = true
    var star2 = false
    var star3 = false


    lateinit var dataModel:AvengersDetailsModel
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_details
    }

    override fun onActivityCreated() {
       initView()
    }

    fun initView(){
        getIntentData()

        setOnclick()
        Glide.with(this@DetailsActivity).load(dataModel.imagePath).into(ivPhoto)

        tvName.text = dataModel.name
        setupRatingData(dataModel.rating)
        setupRating()
    }

    fun setupRatingData(rating:Int){
       when(rating){
           3->{
               star1 = true
               star2 = true
               star3 = true
           }
           2->{
               star1 = true
               star2 = true
               star3 = false
           }
           1->{
               star1 = true
               star2 = false
               star3 = false
           }
       }
    }


    fun setupRating(){
        if(star1){
            ivStar1.background = ContextCompat.getDrawable(this,R.drawable.ic_star_colored)
        }else{
            ivStar1.background = ContextCompat.getDrawable(this,R.drawable.ic_star)

        }
        if(star2){
            ivStar2.background = (ContextCompat.getDrawable(this,R.drawable.ic_star_colored))
        }else{
            ivStar2.background = (ContextCompat.getDrawable(this,R.drawable.ic_star))

        }
        if(star3){
            ivStar3.background = (ContextCompat.getDrawable(this,R.drawable.ic_star_colored))
        }else{
            ivStar3.background = (ContextCompat.getDrawable(this,R.drawable.ic_star))

        }

        var count = 0
        count = if(star1 && star2 && star3){
            3
        }else if(star1 && star2){
            2
        }else{
            1
        }

        updateDatabase(count)


    }
    fun setOnclick(){
        ivStar1.setOnClickListener {
            star1 = true
            star2 = false
            star3 = false
            setupRating()
        }
        ivStar2.setOnClickListener {
            star1 = true
            star2 = true
            star3 = false
            setupRating()
        }
        ivStar3.setOnClickListener {
            star1 = true
            star2 = true
            star3 = true
            setupRating()
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    fun updateDatabase(rating: Int){
        debugLog("check id::${dataModel.id}")
        DatabaseHelper.updateStatus(this,dataModel.id.toInt(),rating)

    }

    fun getIntentData(){
        if(intent.hasExtra("data")){
            dataModel = intent.getSerializableExtra("data") as AvengersDetailsModel
        }
    }
}