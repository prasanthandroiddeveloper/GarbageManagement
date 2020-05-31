package com.bsss.garbagemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "BhanuRam";

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NAME = "name";
    public static final String ID = "ID";
    private static final String KEY_EMAIL = "email";
    public static final String PASSWORD="PASSWORD";
    public static  final  String KEY_Address1="Address1";
    public static  final  String KEY_Address2="Address2";
    public static  final  String KEY_city="City";
    public static  final  String KEY_Pin="pin";
    public static  final  String KEY_Dis="District";
    public static  final  String KEY_state="State";
    public static  final  String KEY_DESCR="Description";


    private static String TAG = PrefManager.class.getSimpleName();

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_MOBILE_NUMBER, null);
    }

    public void createLogin(String name, String email, String mobile) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE_NUMBER, mobile);
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void checkLogin(){

        if (!this.isLoggedIn()){
            Intent i = new Intent(_context, Login.class);
            _context.startActivity(i);
            ((MainActivity) _context).finish();
        }
    }


    public void createSession(String name,String email,String id,String mobile)
    {
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(ID, id);
        editor.putString(KEY_MOBILE_NUMBER, mobile);
//        editor.putString(PASSWORD, password);
        editor.apply();


    }
    public void InsertData(String Address1,String Address2,String id,String cit,String Code,String Dis, String Description)
    {
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.putString(KEY_Address1,Address1);
        editor.putString(KEY_Address2,Address2);
        editor.putString(ID,id);
        editor.putString(KEY_city,cit);
        editor.putString(KEY_Pin,Code);
        editor.putString(KEY_Dis,Dis);

        editor.putString(KEY_DESCR,Description);



    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(ID, pref.getString(ID, null));
        user.put(KEY_MOBILE_NUMBER,pref.getString(KEY_MOBILE_NUMBER,null));
        user.put(PASSWORD,pref.getString(PASSWORD,null));
        return user;
    }
    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, Login.class);
        _context.startActivity(i);
        ((MainActivity)_context).finish();

    }



}