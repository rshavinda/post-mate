package com.ceylonsolutions.postmate.ui.addpost

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.ceylonsolutions.postmate.R
import com.ceylonsolutions.postmate.base.BaseActivity
import com.ceylonsolutions.postmate.databinding.ActivityAddPostBinding
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.Resource
import com.ceylonsolutions.postmate.data.model.User
import com.ceylonsolutions.postmate.data.type.Status
import com.ceylonsolutions.postmate.util.Helper
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.google.maps.android.ktx.utils.collection.addMarker


@Suppress("DEPRECATION")
class AddPostActivity : BaseActivity <ActivityAddPostBinding>() {
    companion object {
        private val TAG = AddPostActivity::class.java.simpleName
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        const val CAMERA_ZOOM = 6F
    }
    override fun setViewBinding() = ActivityAddPostBinding.inflate(layoutInflater)
    private lateinit var mViewModel: AddPostViewModel
    private lateinit var mAlertDialogInternet: AlertDialog
    private var  mUserData : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        Helper.setScreen(this)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
        finish()
    }

    private fun init(){
        mViewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)
        mViewModel.observeResponse().observe(this, mObserveResponseData)
        initInternetConnectionErrorDialog()

        mUserData = intent.getParcelableExtra(EXTRA_USER_DATA)

        if (mUserData != null) {
            val user: User = mUserData as User

            getViewBinding().viewUserData.visibility = View.VISIBLE
            getViewBinding().viewSendPost.visibility = View.GONE

            getViewBinding().textViewUserName.text = user.username
            getViewBinding().textViewName.text = displayDetails(R.string.name, user.name)
            getViewBinding().textViewEmail.text = displayDetails(R.string.email, user.email)
            getViewBinding().textViewWebSite.text = displayDetails(R.string.website, user.website)
            getViewBinding().textViewPhone.text = displayDetails(R.string.phone, user.phone)
            getViewBinding().textViewCompany.text = displayDetails(R.string.company, user.company.name)
            getViewBinding().textViewAddress.text = displayDetails(R.string.address, user.getFullAddressString())

            val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            lifecycle.coroutineScope.launchWhenCreated {

                val googleMap = mapFragment.awaitMap()
                val latitude: Double = user.address.geo.lat.toDouble()
                val longitude: Double = user.address.geo.lng.toDouble()
                val location = LatLng(latitude, longitude)

                googleMap.awaitMapLoad()
                googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                location,
                                CAMERA_ZOOM
                        )
                )

                addMarker(MarkerManager(googleMap), location, user.name)
            }
        }

        getViewBinding().buttonAddPost.setOnClickListener {
            sendPost()
        }
    }

    private fun displayDetails(resId: Int, value : String): String{
       return resources.getString(resId) + value
    }

    private fun addMarker(markerManager: MarkerManager, location : LatLng, name :String) {
        val markerCollection: MarkerManager.Collection = markerManager.newCollection()

        markerCollection.addMarker {
            position(location)
            icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            title(resources.getString(R.string.location_title, name))
        }

        markerCollection.setOnMarkerClickListener { marker ->
            Toast.makeText(
                this,
                 marker.title,
                Toast.LENGTH_SHORT
            ).show()

            false
        }
    }

    private fun sendPost(){
        if(getViewBinding().editTextTitle.text.trim().toString().isEmpty()){
           showMessage(R.string.error_empty_title)
            return
        }
        else if(getViewBinding().editTextPostBody.text.trim().toString().isEmpty()){
            showMessage(R.string.error_empty_message)
            return
        }

        showLoading()
        val post = Post()
        post.userId = 1
        post.title = getViewBinding().editTextTitle.text.trim().toString()
        post.body = getViewBinding().editTextPostBody.text.trim().toString()

        mViewModel.sendPost(post)
    }

    private fun initInternetConnectionErrorDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.no_internet)
        builder.setMessage(R.string.no_internet_error)
        builder.setIcon(R.drawable.ic_information)

        builder.setPositiveButton(R.string.button_retry){ dialogInterface, which ->
            Handler().postDelayed({
                sendPost()
            },2000L)
            dialogInterface.dismiss()
        }

        builder.setNeutralButton(R.string.button_cancel){dialogInterface , which ->
            dialogInterface.dismiss()
        }

        mAlertDialogInternet = builder.create()
        mAlertDialogInternet.setCancelable(false)
    }

    private val mObserveResponseData: Observer<in Resource<Post?>> = Observer { resource ->
        hideLoading()
        Log.d(TAG, "Observe Response Data: ")

        when (resource.status) {
            Status.SUCCESS -> {
                showMessage(R.string.success_post_post)

                Handler().postDelayed({
                    this.finish()
                },500L)

            }
            Status.ERROR -> {
                showErrorMessage(resource.message)
            }
            Status.NO_INTERNET -> {
                if (!mAlertDialogInternet.isShowing) {
                    mAlertDialogInternet.show()
                }
            }
            else -> {
            }
        }
    }
}