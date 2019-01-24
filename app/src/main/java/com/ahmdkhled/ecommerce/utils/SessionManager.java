package com.ahmdkhled.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREFS="prefs";
    private static final String SESSION_KEY="session_key";
    private static final String USERNAME_KEY="user_name";
    private static final String EMAIL_KEY="user_email";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
    }

    public void saveSession(long userId,String name,String email){
        editor=sharedPreferences.edit();
        editor.putLong(SESSION_KEY,userId);
        editor.putString(USERNAME_KEY,name);
        editor.putString(EMAIL_KEY,email);
        editor.apply();
    }

    boolean sessionExist(){
        long userId=sharedPreferences.getLong(SESSION_KEY,-1);
        return userId > -1;
    }



}
