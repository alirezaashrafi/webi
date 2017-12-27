package com.ashrafi.webi.classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;


final class CacheSql extends SQLiteOpenHelper {

    private static final String DB_NAME = "webi";
    private static final int DB_VERSION = 1;
    private static final String COL_LINK = "key";
    private static final String COL_ID = "id";
    private static final String COL_JSONARRAY = "value";
    private static HashMap<String, String> fast = new HashMap<>();
    private final String TAG = this.getClass().getName();
    private final String TABLE = "webi";
    private Handler handler;


    private Context context;
    private String key = "";

    public CacheSql(Context context, String key) {
        super(context, DB_NAME, null, DB_VERSION);
        this.key = key;
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String SQL_COMMAND_CREATE_POSTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                    TABLE + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_LINK + " TEXT, " +
                    COL_JSONARRAY + " TEXT  );";

            sqLiteDatabase.execSQL(SQL_COMMAND_CREATE_POSTS_TABLE);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    @SuppressLint("StaticFieldLeak")
    public void get(final DataRecived dataRecived) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                final SQLiteDatabase sqLiteDatabase = CacheSql.this.getReadableDatabase();

                CheckExist checkExist = new CheckExist(sqLiteDatabase, TABLE, COL_LINK,key);

                checkExist.isExist(new CheckExist.IsExist() {
                    @Override
                    public void isExist(Boolean exist) {
                        if (exist) {
                            Cursor cursor = null;
                            try {
                                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COL_LINK + " = '" + key + "' ", null);
                                if (cursor != null) {
                                    cursor.moveToFirst();
                                    final String text = cursor.getString(2);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {


                                                dataRecived.data((text));
                                            } catch (Exception e) {
                                                Log.e(TAG, "run: " + e);
                                            }
                                        }
                                    });
                                    cursor.close();
                                    sqLiteDatabase.close();

                                }
                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            dataRecived.data("");
                                        } catch (Exception e) {
                                            Log.e(TAG, "run: " + e);
                                        }
                                    }
                                });
                                sqLiteDatabase.close();
                                if (cursor != null) {
                                    cursor.close();
                                }
                            }
                        } else {
                            sqLiteDatabase.close();
                        }
                    }
                });

                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }


    @SuppressLint("StaticFieldLeak")
    public void put(final String value) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {


                final SQLiteDatabase sqLiteDatabase = CacheSql.this.getReadableDatabase();

                final ContentValues contentValues;
                contentValues = new ContentValues();
                contentValues.put(COL_LINK, key);
                contentValues.put(COL_JSONARRAY, value);

                CheckExist checkExist = new CheckExist(sqLiteDatabase, TABLE, COL_LINK,key);

                checkExist.isExist(new CheckExist.IsExist() {
                    @Override
                    public void isExist(Boolean exist) {
                        try {
                            if (!exist) {
                                sqLiteDatabase.insert(TABLE, null, contentValues);
                            } else {
                                String where = COL_LINK + "=?";
                                String[] whereArgs = new String[]{String.valueOf(key)};
                                sqLiteDatabase.update(TABLE, contentValues, where, whereArgs);
                            }

                            sqLiteDatabase.close();
                        } catch (Exception e) {
                            sqLiteDatabase.close();
                        }
                    }
                });
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



    }


    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }


    public interface DataRecived {
        void data(String string);
    }



    public static class CheckExist {
        private SQLiteDatabase sqLiteDatabase;
        private String table = "";
        String from = "";

        String key = "";
        public CheckExist(SQLiteDatabase sqLiteDatabase, String table, String from,String key) {
            this.key = key;
            this.sqLiteDatabase = sqLiteDatabase;
            this.table = table;
            this.from = from;
        }

        @SuppressLint("StaticFieldLeak")
        public void isExist(final IsExist isExist) {


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    Cursor cursor = null;
                    try {
                        String selectString = "SELECT * FROM " + table + " WHERE " + from + " =?";
                        cursor = sqLiteDatabase.rawQuery(selectString, new String[]{key});
                        cursor.moveToFirst();
                        if (cursor.getString(1).equals(key)) {
                            isExist.isExist(true);
                        }

                    } catch (Exception e) {
                        isExist.isExist(false);

                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }


                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }


        interface IsExist {
            void isExist(Boolean exist);
        }

    }
}
