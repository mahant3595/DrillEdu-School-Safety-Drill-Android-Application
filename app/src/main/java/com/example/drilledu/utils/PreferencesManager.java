package com.example.drilledu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "drilledu_prefs";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_STUDENT_CLASS = "student_class";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";

    private final SharedPreferences prefs;

    public PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getStudentName() {
        return prefs.getString(KEY_STUDENT_NAME, "Student Name");
    }

    public void setStudentName(String name) {
        prefs.edit().putString(KEY_STUDENT_NAME, name).apply();
    }

    public String getStudentClass() {
        return prefs.getString(KEY_STUDENT_CLASS, "Class 10 - Section A");
    }

    public void setStudentClass(String className) {
        prefs.edit().putString(KEY_STUDENT_CLASS, className).apply();
    }

    public boolean areNotificationsEnabled() {
        return prefs.getBoolean(KEY_NOTIFICATIONS, true);
    }

    public void setNotificationsEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply();
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }
}