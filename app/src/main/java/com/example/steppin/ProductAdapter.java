package com.example.steppin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHelper;
import Model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Product> products;
    private final DatabaseHelper databaseHelper;

    ProductAdapter(Context context, List<Product> productList) {
        this.products = productList;
        this.inflater = LayoutInflater.from(context);
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void filterByPrice(int minPrice, int maxPrice) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                filteredList.add(product);
            }
        }
        products = filteredList;
        notifyDataSetChanged();
    }

    public void filterByBrandAndPrice(String brand, int minPrice, int maxPrice) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : databaseHelper.getAllProducts()) {
            if (product.getBrand().equals(brand) && product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                filteredList.add(product);
            }
        }
        products = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);

        byte[] imageData = product.getImage();
        if (imageData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            Log.d("ProductAdapter", "Image data is null for position: " + position);
        }

        // Устанавливаем другие данные продукта
        holder.nameView.setText(product.getName());
        holder.brandView.setText(product.getBrand());
        holder.priceView.setText(String.valueOf(product.getPrice()) + "$");

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
    }

    public void filterByBrand(String brand) {
        if (brand.equals("All")) {
            products = databaseHelper.getAllProducts();
        } else {
            products = databaseHelper.getProductsByBrand(brand);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, brandView, priceView;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            nameView = view.findViewById(R.id.name);
            brandView = view.findViewById(R.id.brand);
            priceView = view.findViewById(R.id.price);
        }
    }
}
