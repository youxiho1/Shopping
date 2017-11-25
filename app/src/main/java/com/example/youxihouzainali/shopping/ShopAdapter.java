package com.example.youxihouzainali.shopping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by youxihouzainali on 2017/11/26.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Shop> mShopList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ShopImage;
        TextView ShopName;

        public ViewHolder(View view) {
            super(view);
            ShopImage = (ImageView) view.findViewById(R.id.shop_image);
            ShopName = (TextView) view.findViewById(R.id.shop_name);
        }
    }
    public ShopAdapter(List<Shop> shopList) {
        mShopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        ViewHolder holder = new ShopAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ViewHolder holder, int position) {
        Shop shop = mShopList.get(position);
        String imagepath = shop.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        holder.ShopImage.setImageBitmap(bitmap);
        holder.ShopName.setText(shop.getName());
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }
}

