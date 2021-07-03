package com.yaga909.contestyard.session;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yaga909.contestyard.MainActivity;

import java.util.HashMap;

import USER.user_profile;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE =0;
    private static final String PREF_NAME ="LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String EMAIL = "EMAIL";
    public static final String USER = "USER";
    public static final String PASS = "PASS";

    public SessionManager(Context _context) {
        this.context = _context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession(String phone, String pass)
    {
        editor.putBoolean(LOGIN, true);

        editor.putString(EMAIL, phone);
        editor.putString(PASS, pass);
        editor.apply();
    }
    public boolean isLoggin()
    {
        return sharedPreferences.getBoolean(LOGIN, false);

    }

    public void checkLogin()

    {
        if (!this.isLoggin())
        {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((user_profile) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail()
    {
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(USER, sharedPreferences.getString(USER, null));
        user.put(PASS, sharedPreferences.getString(PASS, null));
        return user;

    }
    public void logout()
    {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);

        ((user_profile) context).finish();


    }
}
