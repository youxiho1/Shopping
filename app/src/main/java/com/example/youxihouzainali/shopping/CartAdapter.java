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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> mCartList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView CartImage;
        TextView CartName;
        TextView CartNumber;
        TextView CartPrice;

        public ViewHolder(View view) {
            super(view);
            CartImage = (ImageView) view.findViewById(R.id.cart_image);
            CartName = (TextView) view.findViewById(R.id.cart_name);
            CartNumber = (TextView) view.findViewById(R.id.cart_number);
            CartPrice = (TextView) view.findViewById(R.id.cart_price);
        }
    }
    public CartAdapter(List<Cart> cartList) {
        mCartList = cartList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cart cart = mCartList.get(position);
        String imagepath = cart.getImage();
        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        holder.CartImage.setImageBitmap(bitmap);
        holder.CartPrice.setText(cart.getPrice());
        holder.CartNumber.setText("×"+cart.getNumber());
        holder.CartName.setText(cart.getName());
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }
}

