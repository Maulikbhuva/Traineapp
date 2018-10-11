package softices.com.traineeapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import softices.com.traineeapp.Model.UserModel;

import static com.android.volley.VolleyLog.TAG;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Traineeapp";

    public static final String TABLE_USER = "User";

    public static final String USER_IMAGE = "image";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_GENDER = "GENDER";
    public static final String USER_MOBILENUMBER = "mobilenumber";
    public static final String USER_PASSWORD = "password";

    private ContentValues cValues;
    private  DbHelper dbHelper;
    private SQLiteDatabase dataBase = null;
    private Cursor cursor;

    public DbHelper(Context context) {
        super(context, context.getExternalFilesDir(null).getAbsolutePath()
                + "/" + DATABASE_NAME, null, 1);
    }

    public String tableUser = "CREATE TABLE " +
            TABLE_USER + "("
            + USER_IMAGE + " blob , "
            + USER_EMAIL + " TEXT PRIMARY KEY , "
            + USER_NAME + " TEXT , "
            + USER_GENDER + " TEXT , "
            + USER_MOBILENUMBER + " TEXT ,"
            + USER_PASSWORD + " TEXT )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableUser);
        Log.e("Table is created.....!", "");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertRecord(byte[] image, String name, String email, String gender, String mobilenumber, String password) {
        try {
            dataBase = getWritableDatabase();
            cValues = new ContentValues();

            cValues.put(USER_IMAGE, image);
            cValues.put(USER_NAME, name);
            cValues.put(USER_EMAIL, email);
            cValues.put(USER_GENDER, gender);
            cValues.put(USER_MOBILENUMBER, mobilenumber);
            cValues.put(USER_PASSWORD, password);
            // insert data into database
            dataBase.insert(TABLE_USER, null, cValues);
            dataBase.close();
            return true;
        } catch (Exception e) {
            Log.e("insertRecord", e + "");
        }
        return false;
    }

    public boolean updateRecord(byte[] image, String name, String Email, String gender, String mobilenumber, String password) {

        dataBase = getWritableDatabase();
        cValues = new ContentValues();

        cValues.put(USER_IMAGE, image);
        cValues.put(USER_NAME, name);
        cValues.put(USER_GENDER, gender);
        cValues.put(USER_MOBILENUMBER, mobilenumber);
        cValues.put(USER_PASSWORD, password);

//    Update data from database table
        dataBase.update(DbHelper.TABLE_USER, cValues, USER_EMAIL + " = ?", new String[]{Email});
        dataBase.close();
        return false;
    }

    public boolean isUserLogin(String email, String password) {
        String[] columns = {
                USER_EMAIL,
                USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public void deleteRecord() {
        dataBase = getWritableDatabase();
//    Deleting all records from database table
        dataBase.delete(TABLE_USER, null, null);
        dataBase.close();
    }

    /**
     * \
     * Used to get all data in model using current login user mail.
     *
     * @param
     * @return
     */
//    public UserModel getUserDataByEmail(String value) {
//        UserModel data = new UserModel();
//        try {
//            String query = "SELECT * FROM " + DbHelper.TABLE_USER + " WHERE "
//                    + DbHelper.USER_EMAIL + "='" + value + "';";
//            Cursor cursor = dataBase.rawQuery(query, null);
//
//            if (cursor.getCount() > 0) {
//                cursor.moveToNext();
//                data.setName(cursor.getString(cursor.getColumnIndex(DbHelper
//                        .USER_NAME)));
//                data.setEmail(cursor.getString(cursor.getColumnIndex(DbHelper
//                        .USER_EMAIL)));
//                data.setMobilenumber(cursor.getString(cursor.getColumnIndex(DbHelper
//                        .USER_MOBILENUMBER)));
//                data.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper
//                        .USER_PASSWORD)));
//                data.setPhoto((Bitmap) Utility.getPhoto(cursor.getBlob(cursor.getColumnIndex(DbHelper
//                        .USER_IMAGE))));
//                return data;
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "getUserDataByEmail " + e);
//        }
//        return data;
//    }
public  Cursor getUserData (String email){
     String[] colums = {
             DbHelper.USER_IMAGE,
             DbHelper.USER_NAME,
             DbHelper.USER_EMAIL,
             DbHelper.USER_GENDER,
             DbHelper.USER_MOBILENUMBER,
             DbHelper.USER_PASSWORD
     };
     SQLiteDatabase db =this.getReadableDatabase();
     String selection =USER_EMAIL + " = ? ";
     String[] selectionArgs = {email};
     Cursor curser = db.query(TABLE_USER,
             colums,
             selection,
             selectionArgs,
             null,
             null,
             null);
return  curser;
}

    private static class Utility {
        public static Object getPhoto(byte[] blob) {
            return null;
        }

    }
    public boolean isEmailExist(String mail) {
        try {
            String[] columns = {
                    DbHelper.USER_EMAIL
            };
            // selection criteria
            String selection = DbHelper.USER_EMAIL + " = ?";

            String[] selectionArgs = {mail};

            Cursor cursor = dbHelper.query(DbHelper.TABLE_USER, columns, selection,
                    selectionArgs, null, null, null);
            int Count = cursor.getCount();
            if (Count > 0) {
                return true;
            }

        } catch (Exception e) {
            Log.e(TAG, "isEmailExist " + e);
        }
        return false;
    }

    private Cursor query(String tableUser, String[] columns, String selection, String[] selectionArgs, Object o, Object o1, Object o2) {
        return null;
    }
}
