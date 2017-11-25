package com.example.youxihouzainali.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyShopActivity extends AppCompatActivity {
    private List<Margin> marginList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    String uname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        TextView editText = (TextView) findViewById(R.id.txname);
        editText.setText(uname);
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyShopActivity.this, NewMarginActivity.class);
                intent.putExtra("extra_data", uname);
                startActivity(intent);
            }
        });
        initMargins();              //初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MarginAdapter adapter = new MarginAdapter(marginList);
        recyclerView.setAdapter(adapter);
    }
    private void initMargins() {
            String shopname = null;
            dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Shop", null, "username=?", new String[] {uname}, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    shopname = cursor.getString(cursor.getColumnIndex("shopname"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            cursor = db.query("Margin", null, "shopname = ?", new String[] {shopname}, null, null,null);
            if(cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    String describe = cursor.getString(cursor.getColumnIndex("describe"));
                    String picture = cursor.getString(cursor.getColumnIndex("picture"));
                    Margin m = new Margin(name, describe, price, picture);
                    marginList.add(m);
                }while(cursor.moveToNext());
            }
            cursor.close();
    }
}
