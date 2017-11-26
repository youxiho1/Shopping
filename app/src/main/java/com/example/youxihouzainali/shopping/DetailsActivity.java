package com.example.youxihouzainali.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private List<Details> detailsList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    String uname = null;
    private String shopname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        shopname = intent.getStringExtra("shopname");
        initDetailss();              //初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DetailsAdapter adapter = new DetailsAdapter(detailsList, uname, shopname);
        recyclerView.setAdapter(adapter);
    }

    private void initDetailss() {
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String name = null, describe = null, price = null, picture = null;
        Cursor cursor2 = db.query("Margin", null, "id=?", new String[] {shopname}, null, null, null, null);
        if(cursor2.moveToFirst()) {
            do {
                name = cursor2.getString(cursor2.getColumnIndex("name"));
                picture = cursor2.getString(cursor2.getColumnIndex("picture"));
                price = cursor2.getString(cursor2.getColumnIndex("price"));
                describe = cursor2.getString(cursor2.getColumnIndex("describe"));
            }while(cursor2.moveToNext());
        }
        cursor2.close();
        Details m = new Details(name, describe, price, picture);
        detailsList.add(m);
    }
}
