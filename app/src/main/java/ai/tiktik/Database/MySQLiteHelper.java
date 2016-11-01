package ai.tiktik.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ABHISHEK on 10/27/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    //here im giving my database name
    public static final String DATABASE_NAME = "tiktik";

    //if we increase the data base  version we need to call  onUpgrade() method
    public static final int DATABASE_VERSION = 1;


    // attribute name of the table
    public static final String DATA_NAME = "data_name";
    public static final String DATA_VALUE = "data_value";







    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
