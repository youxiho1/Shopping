package com.example.youxihouzainali.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import android.content.Intent;

/**
 * Created by youxihouzainali on 2017/11/25.
 */

public class MarginAdapter extends RecyclerView.Adapter<MarginAdapter.ViewHolder> {
    private List<Margin> mMarginList;
    private String ownername;
    private String name1;
    private MyDatabaseHelper dbHelper;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View marginView;
        ImageView MarginImage;
        TextView MarginName;
        TextView MarginDescribe;
        TextView MarginPrice;
        Button Btn_Update;
        Button Btn_Delete;

        public ViewHolder(View view) {
            super(view);
            marginView = view;
            MarginImage = (ImageView) view.findViewById(R.id.margin_image);
            MarginName = (TextView) view.findViewById(R.id.margin_name);
            MarginDescribe = (TextView) view.findViewById(R.id.margin_describe);
            MarginPrice = (TextView) view.findViewById(R.id.margin_price);
            Btn_Update  = (Button) view.findViewById(R.id.update);
            Btn_Delete = (Button) view.findViewById(R.id.delete);
        }
    }
    public MarginAdapter(List<Margin> marginList, String ownername) {
        mMarginList = marginList;
        this.ownername = ownername;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.margin_item, parent, false);
        mContext = parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.Btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new MyDatabaseHelper(mContext, "Shopping.db", null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Margin", "name=?", new String[] {name1});
                Intent intent = new Intent(mContext,MyShopActivity.class);
                intent.putExtra("extra_data", ownername);
                mContext.startActivity(intent);
            }
        });
        holder.Btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewMarginActivity.class);
                intent.putExtra("extra_data", ownername);
                intent.putExtra("flag", 2);
                intent.putExtra("name", name1);
                mContext.startActivity(intent);
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Margin margin = mMarginList.get(position);
        String imagepath = margin.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        name1 = margin.getName();
        holder.MarginImage.setImageBitmap(bitmap);
        holder.MarginPrice.setText(margin.getPrice());
        holder.MarginDescribe.setText(margin.getDescribe());
        holder.MarginName.setText(margin.getName());
    }

    @Override
    public int getItemCount() {
        return mMarginList.size();
    }
}

