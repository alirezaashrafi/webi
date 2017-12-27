package com.ashrafi.webi.classes;
import android.content.Context;
import android.content.SharedPreferences;

final class CacheXml {

    private final SharedPreferences preferences;

    private String tag = "WEBI";
    private String key = "";


     /** (webi library github - by alireza ashrafi - github : alirezaashrafi)
      *
      *  open share pref to save response in xml mode
      *
      */
    protected CacheXml(Context context, String key) {
        this.key = key;
        preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
    }


     /** (webi library github - by alireza ashrafi - github : alirezaashrafi)
      *
      *  if size of data in share pref more than 50 then
      *  remove all data or reset
      *
      */
    protected void put(String value) {
        if (count() >= 50) {
            getEditor().clear();

        }
        if (!contains()){
            getEditor().putString(key, String.valueOf(value)).apply();
        }
    }

    protected String get() {
        return preferences.getString(key, null);
    }


    protected boolean contains() {
        return preferences.contains(key);
    }


    protected long count() {
        return preferences.getAll().size();
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

}