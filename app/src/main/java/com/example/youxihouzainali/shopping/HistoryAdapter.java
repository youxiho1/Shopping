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

/**
 * Created by youxihouzainali on 2017/11/25.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<History> mHistoryList;
    private String username = null;
    private Context mContext;
    private MyDatabaseHelper dbHelper;
    private int idd;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView HistoryImage;
        TextView HistoryName;
        TextView HistoryNumber;
        TextView HistoryPrice;
        TextView HistoryPayflag;
        Button HistoryPay;

        public ViewHolder(View view) {
            super(view);
            HistoryImage = (ImageView) view.findViewById(R.id.history_image);
            HistoryName = (TextView) view.findViewById(R.id.history_name);
            HistoryNumber = (TextView) view.findViewById(R.id.history_number);
            HistoryPrice = (TextView) view.findViewById(R.id.history_price);
            HistoryPayflag = (TextView) view.findViewById(R.id.history_payflag);
            HistoryPay = (Button) view.findViewById(R.id.history_pay);
        }
    }
    public HistoryAdapter(List<History> historyList, String ownername, int id) {
        mHistoryList = historyList;
        username = ownername;
        idd= id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        mContext = parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.HistoryPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new MyDatabaseHelper(mContext, "Shopping.db", null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("payflag", 1);
                db.update("History", values, "id=?", new String[]{Integer.toString(idd)});
                Intent intent = new Intent(mContext, HistoryActivity.class);
                intent.putExtra("extra_data", username);
                mContext.startActivity(intent);
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = mHistoryList.get(position);
        String imagepath = history.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        holder.HistoryImage.setImageBitmap(bitmap);
        holder.HistoryPrice.setText(history.getPrice());
        holder.HistoryNumber.setText("×"+history.getNumber());
        holder.HistoryPayflag.setText(history.getPayflag());
        if(history.getPayflag().equals("已支付")) {
            holder.HistoryPay.setEnabled(false);
        }
        holder.HistoryName.setText(history.getName());
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
}

