package com.paneapple.paneapple.tutor;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Preferences {

    public static final String PREF_NAME = "my_prefrences";

    @SuppressWarnings("deprecation")
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String user_type = "user_type";
    public static final String username = "username";
    public static final String login_type = "login_type";
    public static final String student_wallet = "student_wallet";
    public static final String Reciever_id = "Reciever_id";
    public static final String fee = "fee";
    public static final String reciever_name = "Reciever_name";
    public static final String reciever_image = "Reciever_image";
    public static final String user_token = "user_token";
    public static final String reciever_token = "reciever_token";
    public static final String call_student_id = "call_student_id";
    public static final String call_student_name = "call_student_name";
    public static final String call_tutor_id = "call_tutor_id";
    public static final String call_tutor_name = "call_tutor_name";
    public static final String call_tutor_fees = "call_tutor_fees";


    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static void writeArray(Context context, String key, Set<String> defValue) {
        getEditor(context).putStringSet(key, defValue).commit();
    }
    public static Set<String> readArray(Context context, String key, Set<String> defValue) {
        return getPreferences(context).getStringSet(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}
