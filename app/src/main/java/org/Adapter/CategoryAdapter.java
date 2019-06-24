package org.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.Interface.ItemClickListener;
import org.Model.Category;
import org.R;
import org.ShopActivity;
import org.Utils.Common;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    SharedPreferences pref;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("shopId", position+"");                       // 현재 로그인 타입 - 없음
//                editor.commit();

                Common.shopId = position+1;

                Common.currentCategory = categories.get(position);
                context.startActivity(new Intent(context, ShopActivity.class));

            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

