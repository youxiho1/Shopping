package com.example.youxihouzainali.shopping;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<Cart> cartList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    String uname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        Button btn_pay = (Button) findViewById(R.id.btnpay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new MyDatabaseHelper(CartActivity.this, "Shopping.db", null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Cart", null, "username=?", new String[] {uname}, null, null, null, null);
                if(cursor.moveToFirst()) {
                    do {
                        int idd = cursor.getInt(cursor.getColumnIndex("marginid"));
                        int number = cursor.getInt(cursor.getColumnIndex("number"));
                        ContentValues values = new ContentValues();
                        values.put("username", uname);
                        values.put("marginid", idd);
                        values.put("number", number);
                        values.put("payflag", 0);
                        db.insert("History", null, values);
                        values.clear();
                    }while(cursor.moveToNext());
                }
                cursor.close();
                Intent intent = new Intent(CartActivity.this, HistoryActivity.class);
                intent.putExtra("data_extra", uname);
                startActivity(intent);
            }
        });
        initCarts();              //初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CartAdapter adapter = new CartAdapter(cartList);
        recyclerView.setAdapter(adapter);
    }

    private void initCarts() {
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Cart", null, "username=?", new String[] {uname}, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String name = null, picture = null, price = null;
                int idd = cursor.getInt(cursor.getColumnIndex("marginid"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                Cursor cursor2 = db.query("Margin", null, "id=?", new String[] {Integer.toString(idd)}, null, null, null, null);
                if(cursor2.moveToFirst()) {
                    do {
                        name = cursor2.getString(cursor2.getColumnIndex("name"));
                        picture = cursor2.getString(cursor2.getColumnIndex("picture"));
                        price = cursor2.getString(cursor2.getColumnIndex("price"));
                    }while(cursor2.moveToNext());
                }
                cursor2.close();
                Cart m = new Cart(name, number, price, picture);
                cartList.add(m);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
