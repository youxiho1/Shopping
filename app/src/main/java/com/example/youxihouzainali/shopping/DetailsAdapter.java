package com.example.youxihouzainali.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

/**
 * Created by youxihouzainali on 2017/11/26.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder>{
    private List<Details> mDetailsList;
    private MyDatabaseHelper dbHelper;
    private Context mContext;
    private String username;
    private String shopname;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View detailsView;
        ImageView DetailsImage;
        TextView DetailsName;
        TextView DetailsPrice;
        TextView DetailsDescribe;
        Button button;

        public ViewHolder(View view) {
            super(view);
            detailsView = view;
            DetailsImage = (ImageView) view.findViewById(R.id.details_image);
            DetailsName = (TextView) view.findViewById(R.id.details_name);
            DetailsPrice = (TextView) view.findViewById(R.id.details_price);
            DetailsDescribe = (TextView) view.findViewById(R.id.details_describe);
            button = (Button) view.findViewById(R.id.add);
        }
    }
    public DetailsAdapter(List<Details> detailsList, String username, String shopname) {
        mDetailsList = detailsList;
        this.username = username;
        this.shopname = shopname;
    }

    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_item, parent, false);
        mContext = parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idd;
                dbHelper = new MyDatabaseHelper(mContext, "Shopping.db", null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                int position = holder.getAdapterPosition();
                Details details = mDetailsList.get(position);
                String s = details.getName();
                Cursor cursor = db.query("Margin", null, "name=?", new String[]{s}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        idd = cursor.getInt(cursor.getColumnIndex("id"));
                    } while (cursor.moveToNext());
                    values.put("username", username);
                    values.put("number", 1);
                    values.put("marginid", idd);
                    db.insert("Cart", null, values);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailsAdapter.ViewHolder holder, int position) {
        Details details = mDetailsList.get(position);
        String imagepath = details.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        holder.DetailsImage.setImageBitmap(bitmap);
        holder.DetailsName.setText(details.getName());
        holder.DetailsDescribe.setText(details.getDescribe());
        holder.DetailsPrice.setText(details.getPrice());
    }
    @Override
    public int getItemCount() {
        return mDetailsList.size();
    }
}
