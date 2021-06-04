package com.example.selfdrvnassessment.activity


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.util.jar.Manifest


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

        refresh()
    }

    fun initView() {

        if (isAllPermissionGranted(this)){
            setupAdapter()
            getAllList()
        }


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
        data.imagePath = saveDrawableToFolder(R.drawable.spiderman)
        data.rating = 2

        val data2 = AvengersDetailsModel()
        data2.name = "thor"
        data2.imagePath = saveDrawableToFolder(R.drawable.thor)
        data2.rating = 1

        val data3 = AvengersDetailsModel()
        data3.name = "doctor strange"
        data3.imagePath = saveDrawableToFolder(R.drawable.doctor_strange)
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
        displayLoadingDialog()
        DatabaseHelper.fetchAllList(this@MainActivity) {

            if (it.isNotEmpty()) {
                list = it as ArrayList<AvengersListEntity>
                adapter.setData(list)
                checkEmptyList()
            } else {
                debugLog("check  empty")
            }
            hideLoadingDialog()
        }

    }

    fun saveDrawableToFolder(drawableId: Int): String {

        val bm = BitmapFactory.decodeResource(resources, drawableId)
        val capturedPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
        val file = File(capturedPath!!)
        if (!file.exists()) {
            file.mkdir()
        }
        val capturedFile = File(
            file,
            convertDate(System.currentTimeMillis().toString(), "yyyymmdd_hhmmss") + ".png"
        )

        if (!capturedFile.exists()) {
            val outStream = FileOutputStream(capturedFile)
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
        }

        return capturedFile.absolutePath
    }

    fun convertDate(dateInMilliseconds: String, dateFormat: String?): String {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }

    fun isAllPermissionGranted(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAllPermission(activity)
        } else {

            true
        }
    }


    private fun checkAllPermission(activity: Activity): Boolean {

        val write = ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read = ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionNeeded: MutableList<String> = ArrayList()


        if (write != PackageManager.PERMISSION_GRANTED) {
            permissionNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (read != PackageManager.PERMISSION_GRANTED) {
            permissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionNeeded.isNotEmpty()) {

            ActivityCompat.requestPermissions(
                activity,
                permissionNeeded.toTypedArray(),
               permsRequestCode
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permsRequestCode->{
                if (isAllPermissionGranted(this)){
                    setupAdapter()
                    getAllList()
                }
            }
        }

    }


}