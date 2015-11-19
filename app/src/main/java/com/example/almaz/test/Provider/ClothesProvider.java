package com.example.almaz.test.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by almaz on 15.11.2015.
 */
public class ClothesProvider extends ContentProvider {
    final String LOG_TAG = "myLogs";

    //Database
    static final String DB_NAME = "mydb";
    static final int DB_VERSION = 1;

    //Tables
    static final String CLOTHES_TABLE = "clothes";

    //Fields
    static final String CLOTHES_ID = "_id";
    static final String CLOTHES_NAME = "name";
    static final String CLOTHES_LAYOUT = "layout";
    static final String CLOTHES_TEMPERATURE_COEFFICIENT = "temperature_coefficient";
    static final String CLOTHES_STYLE_OFFICIAL = "style_official";
    static final String CLOTHES_STYLE_REGULAR = "style_regular";
    static final String CLOTHES_STYLE_SPORT = "style_sport";
    static final String CLOTHES_STYLE_EVENING = "style_evening";
    static final String CLOTHES_WINDPROOF = "windproof";
    static final String CLOTHES_RAIN_COVER = "rain_cover";

    //Database creating script
    static final String DB_CREATE = "create table " + CLOTHES_TABLE + "(" + CLOTHES_ID + " integer primary key autoincrement, " + CLOTHES_NAME + " text, " + CLOTHES_LAYOUT + " text, " + CLOTHES_TEMPERATURE_COEFFICIENT + " integer, " + CLOTHES_STYLE_OFFICIAL + " boolean, " + CLOTHES_STYLE_REGULAR + " boolean, " + CLOTHES_STYLE_SPORT + " boolean, " + CLOTHES_STYLE_EVENING + " boolean, " + CLOTHES_WINDPROOF + " boolean, "+ CLOTHES_RAIN_COVER + " boolean"  + ");";

    // // Uri
    //authority
    final static String AUTHORITY = "almaz.example.com.test";

    //path
    final static String CLOTHES_PATH = "clothes";

    //united Uri
    public static final Uri CLOTHES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CLOTHES_PATH);

    //data types
    //set of rows
    static final String CLOTHES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CLOTHES_PATH;

    //one row
    static final String CLOTHES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + CLOTHES_PATH;

    // // UriMatcher
    //united Uri
    static final int URI_CLOTHES = 1;

    //Uri with id
    static final int URI_CLOTHES_ID = 2;

    //describing and creating UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CLOTHES_PATH, URI_CLOTHES);
        uriMatcher.addURI(AUTHORITY, CLOTHES_PATH + "/#", URI_CLOTHES_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
//        switch (uriMatcher.match(uri)) {
//            case URI_CLOTHES: // united Uri
//                Log.d(LOG_TAG, "URI_CONTACTS");
//                if (TextUtils.isEmpty(sortOrder)) {
//                    sortOrder = CLOTHES_NAME + " ASC";
//                }
//                break;
//            case URI_CLOTHES_ID: // Uri with ID
//                String id = uri.getLastPathSegment();
//                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
//                if (TextUtils.isEmpty(selection)) {
//                    selection = CLOTHES_ID + " = " + id;
//                } else {
//                    selection = selection + " AND " + CLOTHES_ID + " = " + id;
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("Wrong URI: " + uri);
//        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CLOTHES_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                CLOTHES_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                return CLOTHES_CONTENT_TYPE;
            case URI_CLOTHES_ID:
                return CLOTHES_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_CLOTHES)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(CLOTHES_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CLOTHES_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                Log.d(LOG_TAG, "URI_CONTACTS");
                break;
            case URI_CLOTHES_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CLOTHES_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CLOTHES_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(CLOTHES_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                Log.d(LOG_TAG, "URI_CONTACTS");

                break;
            case URI_CLOTHES_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CLOTHES_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CLOTHES_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(CLOTHES_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            cv.put(CLOTHES_NAME, "T-shirt");
            cv.put(CLOTHES_LAYOUT, "body");
            cv.put(CLOTHES_TEMPERATURE_COEFFICIENT, 5);
            cv.put(CLOTHES_STYLE_OFFICIAL, false);
            cv.put(CLOTHES_STYLE_REGULAR, true);
            cv.put(CLOTHES_STYLE_SPORT, true);
            cv.put(CLOTHES_STYLE_EVENING, false);
            cv.put(CLOTHES_WINDPROOF, true);
            cv.put(CLOTHES_RAIN_COVER, true);
            db.insert(CLOTHES_TABLE, null, cv);

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
