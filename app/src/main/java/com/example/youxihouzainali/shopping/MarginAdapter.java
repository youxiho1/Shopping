package com.example.youxihouzainali.shopping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by youxihouzainali on 2017/11/25.
 */

public class MarginAdapter extends RecyclerView.Adapter<MarginAdapter.ViewHolder> {
    private List<Margin> mMarginList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView MarginImage;
        TextView MarginName;
        TextView MarginDescribe;
        TextView MarginPrice;

        public ViewHolder(View view) {
            super(view);
            MarginImage = (ImageView) view.findViewById(R.id.margin_image);
            MarginName = (TextView) view.findViewById(R.id.margin_name);
            MarginDescribe = (TextView) view.findViewById(R.id.margin_describe);
            MarginPrice = (TextView) view.findViewById(R.id.margin_price);
        }
    }
    public MarginAdapter(List<Margin> marginList) {
        mMarginList = marginList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.margin_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Margin margin = mMarginList.get(position);
        String imagepath = margin.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
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

