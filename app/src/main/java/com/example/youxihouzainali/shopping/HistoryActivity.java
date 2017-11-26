package com.example.youxihouzainali.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    String uname;
    private List<History> historyList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private int temp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        Intent intent= getIntent();
        uname= intent.getStringExtra("extra_data");
        initHistorys();              //初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HistoryAdapter adapter = new HistoryAdapter(historyList, uname,temp);
        recyclerView.setAdapter(adapter);
    }
    private void initHistorys() {
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("History", null, "username=?", new String[] {uname}, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String name = null, picture = null, price = null, flag1 = null;
                int idd = cursor.getInt(cursor.getColumnIndex("marginid"));
                temp = cursor.getInt(cursor.getColumnIndex("id"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int payflag = cursor.getInt(cursor.getColumnIndex("payflag"));
                Cursor cursor2 = db.query("Margin", null, "id=?", new String[] {Integer.toString(idd)}, null, null, null, null);
                if(cursor2.moveToFirst()) {
                    do {
                        name = cursor2.getString(cursor2.getColumnIndex("name"));
                        picture = cursor2.getString(cursor2.getColumnIndex("picture"));
                        price = cursor2.getString(cursor2.getColumnIndex("price"));
                    }while(cursor2.moveToNext());
                }
                cursor2.close();
                if(payflag == 0) {
                    flag1 = "未支付";
                }
                else {
                    flag1 = "已支付";
                }
                History m = new History(name, number, price, flag1, picture);
                historyList.add(m);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
