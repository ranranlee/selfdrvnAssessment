package com.example.selfdrvnassessment.activity



import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfdrvnassessment.BaseActivity
import com.example.selfdrvnassessment.R
import com.example.selfdrvnassessment.adapter.AvengersAdapter
import com.example.selfdrvnassessment.model.AvengersDetailsModel
import com.example.selfdrvnassessment.roomDatabase.database.DatabaseHelper
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : BaseActivity(), AvengersAdapter.ItemClickListener {

    var list: ArrayList<AvengersListEntity> = ArrayList()
    var tempList: ArrayList<AvengersDetailsModel> = ArrayList()
    var adapter = AvengersAdapter()
    private val permsRequestCode = 200
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated() {

        initView()
    }


    override fun onResume() {
        super.onResume()

        if (rcvList.adapter == null){
            setupAdapter()
        }
        displayLoadingDialog()
        getAllList()

    }

    fun initView() {

            setupAdapter()
    }

    fun setupAdapter() {
        rcvList.layoutManager = LinearLayoutManager(this)
        rcvList.adapter = adapter
        adapter.setItemClickListener(this)
    }

    fun getAllList() {

        DatabaseHelper.fetchAllList(this@MainActivity) {

            if (it.isNotEmpty()) {
                list = it as ArrayList<AvengersListEntity>
                adapter.setData(list)
                checkEmptyList()
                hideLoadingDialog()
            } else {

                addData()
            }

        }

    }

    fun checkEmptyList() {

        if (list.isEmpty()) {
            rcvList.visibility = View.GONE
            tvEmptyList.visibility = View.VISIBLE
        } else {
            rcvList.visibility = View.VISIBLE
            tvEmptyList.visibility = View.GONE
        }
    }

    fun addData() {

        val data = AvengersDetailsModel()
        data.name = "spiderman"
        data.imagePath = saveDrawableToFolder(R.drawable.spiderman, data.name)
//        data.imagePath = ""
        data.rating = 2

        val data2 = AvengersDetailsModel()
        data2.name = "thor"
        data2.imagePath = saveDrawableToFolder(R.drawable.thor, data2.name)
//        data2.imagePath = ""
        data2.rating = 1

        val data3 = AvengersDetailsModel()
        data3.name = "doctor strange"
        data3.imagePath = saveDrawableToFolder(R.drawable.doctor_strange, data3.name)
        data3.rating = 3


        tempList.add(data)
        tempList.add(data2)
        tempList.add(data3)

        saveToDB(tempList)

    }

    override fun onItemClick(model: AvengersListEntity) {
        toDetailsActivity(model)
    }

    fun toDetailsActivity(model: AvengersListEntity) {

        val dataPass = AvengersDetailsModel()
        dataPass.id = model.id.toString()
        dataPass.name = model.name
        dataPass.imagePath = model.imagePath
        dataPass.rating = model.rating


        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("data", dataPass)
        startActivity(intent)
    }

    fun saveToDB(list: ArrayList<AvengersDetailsModel>) {

        for (i in list.indices) {
            val data = AvengersListEntity(
                name = list[i].name,
                imagePath = list[i].imagePath,
                rating = list[i].rating

            )
            DatabaseHelper.addTodoList(this, data)
        }

        Handler(Looper.getMainLooper()).postDelayed({

            refresh()
        }, 3000)
    }


    fun refresh() {

        DatabaseHelper.fetchAllList(this@MainActivity) {
            hideLoadingDialog()
            if (it.isNotEmpty()) {
                list = it as ArrayList<AvengersListEntity>
                adapter.setData(list)
                checkEmptyList()
            }

        }

    }

    fun saveDrawableToFolder(drawableId: Int, name: String): String {


        val bm = BitmapFactory.decodeResource(resources, drawableId)
        val capturedPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
        val file = File(capturedPath!!)
        if (!file.exists()) {
            file.mkdir()
        }
        val capturedFile = File(
            file,
            "$name.png"
        )

        if (!capturedFile.exists()) {
            val outStream = FileOutputStream(capturedFile)

                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()


        }

        return capturedFile.absolutePath
    }

}