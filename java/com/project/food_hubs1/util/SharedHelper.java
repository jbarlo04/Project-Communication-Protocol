package com.project.food_hubs1.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    public  SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;

   static SharedHelper sharedHelper;
    public static SharedHelper getinstance(){
        if (sharedHelper==null)
        {
            sharedHelper = new SharedHelper();
        }
         return sharedHelper;

     }

    public  void putKey(Context context, String Key, String Value) {

        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();

    }

    public  String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;

    }


    public  void clearSharedPreferences(Context context)
    {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

    }




}
