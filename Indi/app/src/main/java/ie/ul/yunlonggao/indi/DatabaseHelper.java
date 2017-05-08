package ie.ul.yunlonggao.indi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATE = "cost_date";
    public static final String COST_TITLE = "cost_title";
    public static final String INFO = "info";

    public DatabaseHelper(Context context) {
        super(context, "cost_info", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists info("+
                "id integer primary key,"+
                "cost_title varchar,"+
                "cost_date varchar,"+
                "cost_money varchar)");
    }
    public void insertCost(CostBean costBean){

        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COST_TITLE,costBean.costTile);
        cv.put(COST_DATE,costBean.costDate);
        cv.put(COST_MONEY,costBean.costMoney);
        database.insert(INFO,null,cv);

    }
    public Cursor getAllCostData(){
        SQLiteDatabase database=getWritableDatabase();
        return  database.query(INFO,null,null,null,null,null,COST_DATE+" ASC");
    }
    public void deleteAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(INFO,null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
