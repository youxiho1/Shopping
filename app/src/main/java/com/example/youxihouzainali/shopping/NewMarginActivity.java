package com.example.youxihouzainali.shopping;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewMarginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_margin);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        Button addPicture = (Button) findViewById(R.id.add_Picture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btn_Enter = (Button) findViewById(R.id.Btn_Enter);
        btn_Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_name = (EditText) findViewById(R.id.edit_name);
                EditText editText_describe = (EditText) findViewById(R.id.edit_describe);
                EditText editText_price = (EditText)findViewById(R.id.edit_price);
                String name = editText_name.getText().toString();
                String descirbe = editText_describe.getText().toString();
                double price = Double.parseDouble(editText_price.getText().toString());

            }
        });
    }
}
