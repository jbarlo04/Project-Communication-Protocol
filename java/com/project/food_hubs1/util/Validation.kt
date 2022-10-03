package com.project.food_hubs1.util;

import android.app.Activity
import android.util.Patterns
import android.widget.Toast
import com.project.food_hubs1.R
import java.util.regex.Pattern

class Validation {


    fun loginvalidate(context: Activity, emal:String,passsword:String):Boolean {

         if (emal.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_email))
            return false
        }
       /* else if (!Patterns.EMAIL_ADDRESS.matcher(emal).matches())
        {
            showtoast(context,context.getString(R.string.validation_valid_email))
            return false
        }*/
         else if (passsword.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_enterpassword))
             return false
         }

         else{
             return true
         }
    }

    fun registersocialvalidate(context: Activity, name:String,email:String,phone:String,check : Boolean):Boolean{

        if (name.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_enter_name))
            return false
        }

        else if (email.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_email))
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            showtoast(context,context.getString(R.string.validation_valid_email))
            return false
        }


        else if (phone.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_phone))
            return false
        }

        else if (!Patterns.PHONE.matcher(phone).matches())
        {
            showtoast(context,context.getString(R.string.validation_valid_phonenumber))
            return false
        }





        else if (!check)
        {
            showtoast(context,context.getString(R.string.validation_termsandcondition))
            return false
        }


        else{
            return true
        }



    }


    fun registervalidate(context: Activity, name:String,email:String,phone:String,pass:String):Boolean{

         if (name.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_enter_name))
            return false
        }

         else if (email.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_email))
             return false
         }
         else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
         {
             showtoast(context,context.getString(R.string.validation_valid_email))
             return false
         }


         else if (phone.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_phone))
             return false
         }

         else if (!isValidMobile(phone))
         {
             showtoast(context,context.getString(R.string.validation_valid_phonenumber))
             return false
         }
        else if (pass.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_enterpassword))
             return false
         }
         else if (pass.length<6)
         {
             showtoast(context,context.getString(R.string.vakudation_6digitpass))
             return false
         }



        else{
             return true
         }



    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
           phone.length == 10
        } else false
    }
    fun registercartervalidate(context: Activity, name:String,bname:String,email:String,phone:String,address:String,city:String,pass:String):Boolean{

         if (name.isEmpty())
        {
            showtoast(context,context.getString(R.string.validation_enter_name))
            return false
        }
        else if (bname.isEmpty())
        {
            showtoast(context,"Please enter your business name")
            return false
        }

         else if (email.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_email))
             return false
         }
         else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
         {
             showtoast(context,context.getString(R.string.validation_valid_email))
             return false
         }


         else if (phone.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_phone))
             return false
         }

         else if (!isValidMobile(phone))
         {
             showtoast(context,context.getString(R.string.validation_valid_phonenumber))
             return false
         }
        else if (address.isEmpty())
        {
            showtoast(context,"Please enter your address")
            return false
        }

         else if (city.isEmpty())
        {
            showtoast(context,"Please enter your city")
            return false
        }
        else if (pass.isEmpty())
         {
             showtoast(context,context.getString(R.string.validation_enterpassword))
             return false
         }
         else if (pass.length<6)
         {
             showtoast(context,context.getString(R.string.vakudation_6digitpass))
             return false
         }



        else{
             return true
         }



    }



    fun showtoast(context: Activity,message:String){

        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
       // Snackbar.make(context.window.decorView.rootView, message, Snackbar.LENGTH_LONG).show();
    }



}