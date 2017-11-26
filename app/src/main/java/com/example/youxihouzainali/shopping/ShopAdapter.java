package com.example.youxihouzainali.shopping;

import android.content.Context;
import android.content.Intent;
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
    private Context mContext;
    private String username;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View shopView;
        ImageView ShopImage;
        TextView ShopName;

        public ViewHolder(View view) {
            super(view);
            shopView = view;
            ShopImage = (ImageView) view.findViewById(R.id.shop_image);
            ShopName = (TextView) view.findViewById(R.id.shop_name);
        }
    }
    public ShopAdapter(List<Shop> shopList, String name1) {
        mShopList = shopList;
        username = name1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        mContext = parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Shop shop = mShopList.get(position);
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("extra_data", username);
                intent.putExtra("shopname", shop.getName());
                mContext.startActivity(intent);
            }
        });
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

