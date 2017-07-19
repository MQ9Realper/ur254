package com.patriot.ur254.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dennismwebia on 04/03/17.
 */

public class SharedPreference {
    private Context context = null;
    private SharedPreferences app_prefs;

    public SharedPreference(Context context) {
        app_prefs = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE);
        context = context;
    }

    public void SaveFirstName(String first_name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("first_name", first_name);
        edit.apply();
    }

    public String GetFirstName() {
        return app_prefs.getString("first_name", "");
    }

    public void SaveLastName(String last_name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("last_name", last_name);
        edit.apply();
    }

    public String GetLastName() {
        return app_prefs.getString("last_name", "");
    }

    public void SaveEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("email", email);
        edit.apply();
    }

    public String GetEmail() {
        return app_prefs.getString("email", "");
    }

    public void SaveConstituency(String constituency) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("constituency", constituency);
        edit.apply();
    }

    public String GetConstituency() {
        return app_prefs.getString("constituency", "");
    }

    public void SaveCounty(String county) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("county", county);
        edit.apply();
    }

    public String GetCounty() {
        return app_prefs.getString("county", "");
    }

    public void SaveWard(String ward) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("ward", ward);
        edit.apply();
    }

    public String GetWard() {
        return app_prefs.getString("ward", "");
    }

    public void SavePhoneNumber(String phone_number) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("phone_number", phone_number);
        edit.apply();
    }

    public String GetPhoneNumber() {
        return app_prefs.getString("phone_number", "");
    }

    public void SaveUserName(String username){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("username", username);
        edit.apply();
    }

    public String GetUsername(){
        return app_prefs.getString("username", "");
    }

    public boolean getIsLoggedIn() {
        return app_prefs.getBoolean("logged_in", false);
    }

    public void putIsLoggedIn(boolean logged_in) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean("logged_in", logged_in);
        edit.apply();
    }

    public void SaveSupporterType(String supporter_type){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("supporter_type", supporter_type);
        edit.apply();
    }

    public String GetSupporterType() {
        return app_prefs.getString("supporter_type", "S");
    }

    public String GetPhotoUrl() {
        return app_prefs.getString("photo_url", "");
    }

    public void SavePhotoUrl(String photo_url){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("photo_url", photo_url);
        edit.apply();
    }

    public boolean getcontactSaved() {
        return app_prefs.getBoolean("contactssaved", false);
    }

    public void putcontactSaved(boolean contactssaved) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean("contactssaved", contactssaved);
        edit.apply();
    }
}

