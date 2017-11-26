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

public class ShoppingActivity extends AppCompatActivity {
    String uname = null;
    private List<Shop> shopList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        final Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        Button btn_cart = (Button)findViewById(R.id.cart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this, CartActivity.class);
                intent.putExtra("extra_data", uname);
                startActivity(intent);
            }
        });
        initShops();              //初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ShopAdapter adapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(adapter);
    }

    private void initShops() {
        String shopname = null;
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Shop", null, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("shopname"));
                String picture = cursor.getString(cursor.getColumnIndex("shoppicture"));
                Shop m = new Shop(name, picture);
                shopList.add(m);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
