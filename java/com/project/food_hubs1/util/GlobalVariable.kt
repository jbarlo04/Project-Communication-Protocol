package com.project.food_hubs1.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.project.food_hubs1.R
import java.text.SimpleDateFormat
import java.util.*


class GlobalVariable() {



    companion object{
        const val Encryptionkey = "m1a9n1i2n1d9e9r0p0a1l0s4i1n9g9h0"
         var currentlanguage = "en"
         const val BaseUrl = "http://50.18.93.201/crowdsurfer/public/api/"
         const val GoogleBaseUrl = "https://maps.googleapis.com/maps/api/"
         const val SendBirdBaseUrl = "https://api-26DDB6D3-45C5-4CCA-8B89-4E98511E1FDD.sendbird.com/v3/"
       const val GOOGLE_PLACE_API_KEY = "AIzaSyC724wONb0INTlr2Bd3eHxHglF1bEcxaak"
         const val Imagebase = "http://50.18.93.201/crowdsurfer/public/"
         const val SocketUrl = "http://192.168.1.142:4000/"
        var anotherpersonlocationoff = "on"
        var anotherpersonname = ""
          var isTrackdetailvar = 0L


       public fun showmessage(view:View,msg:String)
        {
            Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG)
                .show()
        }



      fun date(time:String):String
      {
          val timeinmilis = time.toLong()
          val dateFormat = SimpleDateFormat("dd MMM yyyy - HH:mm:ss")
          return dateFormat.format(timeinmilis)
      }

        fun getCompleteAddressString(context: Context,LATITUDE: Double, LONGITUDE: Double): String {
            var strAdd = ""
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                    val returnedAddress = addresses[0]
                    val strReturnedAddress = StringBuilder("")

                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                    Log.w("My Current loction", strReturnedAddress.toString())
                } else {
                    Log.w("My Current loction", "No Address returned!")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.w("My Current loction", "Canont get Address!")
            }

            return strAdd
        }


         public fun globaldialog(context: Context,message:String,okselect: OnOkselect) {

                val builder = AlertDialog.Builder(context)
                builder.setMessage(message)
                builder.setPositiveButton("Ok"
                ) { dialog, which ->

                    okselect.onselect()
                    dialog.dismiss()
                }


            builder.setCancelable(false)
                val dialog = builder.create()
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.setOnShowListener(DialogInterface.OnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                })
                dialog.show()

        }


        fun globalyesno_btndialog(context: Context, message:String, okselect: OnOkselectforlocation) {

            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setPositiveButton(context.getString(R.string.yes)
            ) { dialog, which ->

                okselect.yesselect()
                dialog.dismiss()
            }
            builder.setNegativeButton("no"
            ) { dialog, which ->
                okselect.noselect()
                dialog.dismiss()
            }

            builder.setCancelable(false)
            val dialog = builder.create()
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.setOnShowListener(DialogInterface.OnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            })
            dialog.show()

        }


        /************************* distance calculator*********************************/

         fun feet(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Long {
            val locationA = Location("pointA")

            locationA.latitude = lat1
            locationA.longitude = lon1

            val locationB = Location("pointB")

            locationB.latitude = lat2
            locationB.longitude = lon2
            val meter = locationA.distanceTo(locationB)
            val km = meter/1000
            val feet = km*3280.84
            val distance = Math.round(feet)
            return distance
        }

        fun meter(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
            val locationA = Location("pointA")

            locationA.latitude = lat1
            locationA.longitude = lon1

            val locationB = Location("pointB")

            locationB.latitude = lat2
            locationB.longitude = lon2
            val meter = locationA.distanceTo(locationB)
            val distance = Math.round(meter)
            return distance
        }





    }

    interface OnOkselect
    {
        fun onselect()
    }
    interface OnOkselectforlocation
    {
        fun yesselect()
        fun noselect()
    }
    interface apivariables{

        companion object{
            const val refreshtoken = "token?refresh=true"
            const val loginApi = "Signin"
            const val editgroupapi = "editGroup"
            const val checksociallogin = "chkSocialSignin"
            const val forgotpassApi = "forgotPassword"
            const val creategroupAPi = "createGroup"
            const val saveparklocApi = "saveLocation"
            const val editprofile = "editProfile"
            const val logoutApi = "logout"
            const val lang_setting = "langSetting"
            const val updatelatlongApi = "updateLatLong"
            const val homemap = "map"
            const val getnoti = "notificationList"
            const val synchcontact = "SyncContacts"
            const val deletelocationApi = "deleteParkingLocation"
            const val deleteplaceApi = "deleteTrackedPlace"
            const val contactusapi = "contactUs"
            const val unfriend = "unfriend"
            const val subscribe = "subscribe"
            const val getsubscriptionplan = "getSubscription"
            const val checkmyfriend = "checkIsMyFriend"
            const val deleteGroupApi = "deleteGroup"
            const val sendrequest = "sendRequest"
            const val sendtrackrequest = "sendTrackRequest"
            const val endtrackrequest = "endTracking"
            const val acceptdec = "acceptDecline"
            const val locatioonOff = "locationOnOff"
            const val saveplaceApi = "savePlace"
            const val getsynchcontact = "getSyncContactList"
            const val getcontactsearch = "searchContacts"
            const val gettrackedplacesApi = "trackedPlaces"
            const val getpakingplacesApi = "parkingPlaces"
            const val privacypolicy = "privacypolicy"
            const val termsandcondition = "termsconditions"
            const val help = "help"
            const val registerApi = "signUp"
            const val concernlistApi = "concernList"
            const val getfriendlistApi = "friendList"
            const val addconcern = "addConcern"
            const val updateprofileAPi = "profile"
            const val notificationAPi = "notification"
            const val DeletenotificationAPi = "notificationDelete"
        }
    }

    interface variables{
        companion object{
            const val uname = "name"
            const val UserType = "uType"
            const val mobile = "mobile"

            const val bname = "bname"
            const val GaleeryPick = "gallery"
            const val NoImagePick = "noimage"


        }
    }

}