package com.ahmdkhled.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.Expose;

public class SessionManager {
    private static final String PREFS="prefs";
    private static final String SESSION_KEY="session_key";
    private static final String USERNAME_KEY="user_name";
    private static final String EMAIL_KEY="user_email";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
    }

    public void saveSession(long userId,String name,String email){
        editor=sharedPreferences.edit();
        editor.putLong(SESSION_KEY,userId);
        editor.putString(USERNAME_KEY,name);
        editor.putString(EMAIL_KEY,email);
        editor.apply();
    }

    public boolean sessionExist(){
        long userId=sharedPreferences.getLong(SESSION_KEY,-1);
        return userId > -1;
    }
    public long getId(){
        return sharedPreferences.getLong(SESSION_KEY,-1);
    }

    public String getUserName(){
        return sharedPreferences.getString(USERNAME_KEY,null);
    }
    public String getEmail(){
        return sharedPreferences.getString(EMAIL_KEY,null);
    }


    public void logOut(){
        editor=sharedPreferences.edit();
        editor.putLong(SESSION_KEY,-1);
        editor.putString(USERNAME_KEY,null);
        editor.putString(EMAIL_KEY,null);
        editor.apply();
    }



}
