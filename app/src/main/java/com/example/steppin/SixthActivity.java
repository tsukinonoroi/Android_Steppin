package com.example.steppin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Model.Product;
import Data.DatabaseHelper;

public class SixthActivity extends AppCompatActivity {

    private ImageView wishlistImageView;
    private ImageView cartImageView;

    private DatabaseHelper databaseHelper;

    private boolean isWishlistSelected = false;
    private boolean isCartSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        databaseHelper = new DatabaseHelper(this);


        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewBrand = findViewById(R.id.textViewBrand);
        TextView textViewColor = findViewById(R.id.textViewColor);
        TextView textViewSize = findViewById(R.id.textViewSize);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        ImageView imageViewProduct = findViewById(R.id.imageViewProduct);
        wishlistImageView = findViewById(R.id.wishlist);
        cartImageView = findViewById(R.id.cart);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int productId = extras.getInt("productId");
            Product product = databaseHelper.getProductById(productId);

            if (product != null) {
                textViewName.setText(product.getName());
                textViewBrand.setText(product.getBrand());
                textViewColor.setText(product.getColor());
                textViewSize.setText(String.valueOf(product.getSize()));
                textViewPrice.setText(String.valueOf(product.getPrice() + "$"));


                Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
                imageViewProduct.setImageBitmap(bitmap);
            }
        }

        wishlistImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWishlistSelected) {
                    wishlistImageView.setImageResource(R.drawable.wishlistoff);
                    Toast.makeText(SixthActivity.this, "Товар убран из вишлиста", Toast.LENGTH_SHORT).show();
                    isWishlistSelected = false;
                } else {
                    wishlistImageView.setImageResource(R.drawable.wishliston);
                    Toast.makeText(SixthActivity.this, "Товар успешно добавлен в вишлист", Toast.LENGTH_SHORT).show();
                    isWishlistSelected = true;
                }
            }
        });

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCartSelected) {
                    cartImageView.setImageResource(R.drawable.cartoff);
                    Toast.makeText(SixthActivity.this, "Товар убран из корзины", Toast.LENGTH_SHORT).show();
                    isCartSelected = false;
                } else {
                    cartImageView.setImageResource(R.drawable.carton);
                    Toast.makeText(SixthActivity.this, "Товар успешно добавлен в корзину", Toast.LENGTH_SHORT).show();
                    isCartSelected = true;
                }
            }
        });
    }
}
