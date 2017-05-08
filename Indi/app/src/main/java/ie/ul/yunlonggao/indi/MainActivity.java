package ie.ul.yunlonggao.indi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
* Student name:Yunlong Gao
 * Student id:16058925
 */
public class MainActivity extends AppCompatActivity {

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mDatabaseHelper=new DatabaseHelper(this);
        mCostBeanList= new ArrayList<>();
        initCostData();
        ListView costList=(ListView)findViewById(R.id.lv_main);
        mAdapter = new CostListAdapter(this,mCostBeanList);
        costList.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("Record the new cost");
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int which){
                    CostBean costBean =new CostBean();
                        costBean.costTile = title.getText().toString();
                        costBean.costMoney = money.getText().toString();
                        costBean.costDate = date.getYear() +"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth();
                        mDatabaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
/*                builder.setPositiveButton("DELETE",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int which){
                        mDatabaseHelper.deleteAllData();
                        mAdapter.notifyDataSetChanged();
                    }
                });*/
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
    }

    private void initCostData() {

        //mDatabaseHelper.deleteAllData();
/*        for (int i =0;i<2;i++) {
            CostBean costBean=new CostBean();
            costBean.costTile="Cafe";
            costBean.costDate="11-11";
            costBean.costMoney="200";
            mDatabaseHelper.insertCost(costBean);
        }*/
        Cursor cursor=mDatabaseHelper.getAllCostData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                CostBean costBean=new CostBean();
                costBean.costTile = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
                

            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chart) {
            Intent intent=new Intent(MainActivity.this,ChartsActivity.class);
            intent.putExtra("cost_list",(Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }
        else if (id== R.id.action_deleteAllData){
            mDatabaseHelper.deleteAllData();
            mCostBeanList.clear();
            mAdapter.notifyDataSetChanged();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
