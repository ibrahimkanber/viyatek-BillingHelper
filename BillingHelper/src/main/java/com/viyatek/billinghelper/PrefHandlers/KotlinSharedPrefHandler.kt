package com.viyatek.facefind.helpers

import android.content.Context
import java.util.*

class KotlinSharedPrefHandler(val context: Context)
{
    val SUBSCRIPTION_TOKEN = arrayOf("subscription_token", "vcqrjjvmvrv")
    val SUBSCRIPTION_EXPIRATION_DATE = arrayOf("expiration_date", "vmerkvmerokm")
    val SUBSCRIBED = arrayOf("subscribed", "vmewfmwopeaad")
    val SUBSCRIPTION_TYPE = arrayOf("subscription_status", "vejvnrınvıuvn")


    fun ApplyPrefs(tag: Array<String>, value: String) {
        var value = value
        val sharedPrefs = context.getSharedPreferences(Companion.NEW_USER, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val sharedPrefsEncryption = SharedPrefEncKotlin()
        value = sharedPrefsEncryption.encrypt(value)
        editor.putString(tag[1], value)
        editor.apply()
    }

    fun ApplyPrefs(tag: Array<String>, value: Int) {
        val sharedPrefs =
            context.getSharedPreferences(Companion.NEW_USER, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val sharedPrefsEncryption = SharedPrefEncKotlin()
        val encryptedStringValue = sharedPrefsEncryption.encrypt(value.toString())
        editor.putString(tag[1], encryptedStringValue)
        editor.apply()
    }

    fun GetPref(tag: Array<String>): RowDataKotlin? {
        val sharedPrefs =
            context.getSharedPreferences(Companion.NEW_USER, Context.MODE_PRIVATE)
        val sharedPrefEncKotlin = SharedPrefEncKotlin()
        val retrievedString = sharedPrefs.getString(tag[1], null)
        val resolvedString = sharedPrefEncKotlin.resolveEncrypt(retrievedString!!)
        return if (resolvedString == null || resolvedString == "") {
            RowDataKotlin(tag[0], "")
        } else RowDataKotlin(tag[0], resolvedString)
    }

    fun CheckUserExists(context: Context, userID: String?): Boolean {
        val sharedPref = context.getSharedPreferences(userID, Context.MODE_PRIVATE)
        return sharedPref.contains(SUBSCRIPTION_TOKEN[1])
    }

    fun CreateLocalAccount(context: Context) {
        val sharedPrefEncKotlin = SharedPrefEncKotlin()
        val sharedPref = context.getSharedPreferences(Companion.NEW_USER, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val c = Calendar.getInstance()
        val today = c[Calendar.DAY_OF_MONTH]


        editor.putString(SUBSCRIPTION_TOKEN[1], sharedPrefEncKotlin.encrypt("0"))
        editor.putString(SUBSCRIPTION_EXPIRATION_DATE[1], sharedPrefEncKotlin.encrypt("0"))
        editor.putString(SUBSCRIBED[1], sharedPrefEncKotlin.encrypt("0"))
        editor.putString(SUBSCRIPTION_TYPE[1], sharedPrefEncKotlin.encrypt("not_subscribed"))

        editor.apply()
    }

    companion object {
        //New User ID
        val NEW_USER = "VIYATEK_BILLING_PREF"
    }
}