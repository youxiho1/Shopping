package com.example.youxihouzainali.shopping;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewMarginActivity extends AppCompatActivity {

    String uname = null;
    private String imagePath = null;
    private String marginname = null;
    public static final int CHOOSE_PHOTO = 2;
    private int flag;
    private MyDatabaseHelper dbHelper;
    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_margin);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        Intent intent = getIntent();
        uname = intent.getStringExtra("extra_data");
        marginname = intent.getStringExtra("name");
        flag = intent.getIntExtra("flag",1);
        TextView textView = (TextView) findViewById(R.id.textview_username);
        textView.setText(uname);
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        dbHelper.getWritableDatabase();
        picture = (ImageView) findViewById(R.id.picture);
        Button addPicture = (Button) findViewById(R.id.add_Picture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(NewMarginActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewMarginActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
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
                String price = editText_price.getText().toString();
                if(imagePath.equals("") || name.equals("") || descirbe.equals("") || price.equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewMarginActivity.this);
                    dialog.setTitle("信息不完整");
                    dialog.setMessage("您没有填全应填的信息");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                    return;
                }
                /*if(Double.parseDouble(price) == 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewMarginActivity.this);
                    dialog.setTitle("信息错误");
                    dialog.setMessage("价格不能设置为0元");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                    return;
                }
                */
                String shopname = null;
                dbHelper = new MyDatabaseHelper(NewMarginActivity.this, "Shopping.db", null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Shop", null, "username=?", new String[] {uname}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        shopname = cursor.getString(cursor.getColumnIndex("shopname"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                ContentValues values = new ContentValues();
                values.put("picture", imagePath);
                values.put("describe", descirbe);
                values.put("price", price);
                values.put("name", name);
                values.put("shopname", shopname);
                if(flag == 1) {
                    db.insert("Margin", null, values);
                    values.clear();
                }
                else if(flag == 2) {
                    db.update("Margin", values, "name=?", new String[]{marginname});
                }
                Intent intent = new Intent(NewMarginActivity.this, MyShopActivity.class);
                intent.putExtra("extra_data", uname);
                startActivity(intent);
            }
        });
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }
                else {
                    Toast.makeText(this, "您拒绝了我们的权限请求", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    }
                    else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(uri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage();
        }
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage();
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage() {
        if(imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }
        else {
            Toast.makeText(NewMarginActivity.this, "没有找到图片", Toast.LENGTH_SHORT).show();
        }
    }
}
