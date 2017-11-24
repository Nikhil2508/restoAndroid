package com.example.administrator.restoapp.ui;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.restoapp.R;
import com.example.administrator.restoapp.models.ProductModel;

import java.util.List;

/**
 * Created by Administrator on 11/24/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.PVH> {

    List<ProductModel.Response> list;
    Context context;


    public ProductAdapter(List<ProductModel.Response> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false);
        return new PVH(view);
    }

    @Override
    public void onBindViewHolder(PVH holder, int position) {

        ProductModel.Response response = list.get(position);

        holder.productName.setText(response.title);
        holder.productPrice.setText("$ "+ response.price);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PVH extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,productPrice, buyNow;

        public PVH(View itemView) {
            super(itemView);

            productImage = (ImageView) itemView.findViewById(R.id.iv_product_photo);
            productName = (TextView) itemView.findViewById(R.id.tv_product_name);
            buyNow = (TextView) itemView.findViewById(R.id.tv_buy_btn);
            productPrice = (TextView) itemView.findViewById(R.id.tv_product_price);


        }
    }
}
