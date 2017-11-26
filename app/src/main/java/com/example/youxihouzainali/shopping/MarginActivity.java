package com.example.youxihouzainali.shopping;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MarginActivity extends AppCompatActivity {

    String uname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarginActivity.this, NewMarginActivity.class);
                intent.putExtra("extra_data", uname);
                startActivity(intent);
            }
        });
    }
}
