package com.example.steppin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Data.DatabaseHelper;
import Model.Product;

public class FourActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        List<Product> productList = databaseHelper.getAllProducts();

        productAdapter = new ProductAdapter(this, productList);
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(FourActivity.this, SixthActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerViewProducts.setAdapter(productAdapter);

        ImageView logoImageView = findViewById(R.id.logo);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBrandSelectionDialog();
            }
        });
        ImageView userIconImageView = findViewById(R.id.userIconImageView);
        userIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourActivity.this, ActivityFifth.class);
                startActivity(intent);
            }
        });
    }


    private void showBrandSelectionDialog() {
        List<String> brands = databaseHelper.getAllBrands();
        brands.add(0, "All");

        CharSequence[] brandArray = brands.toArray(new CharSequence[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите бренд")
                .setItems(brandArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedBrand = brandArray[which].toString();
                        productAdapter.filterByBrand(selectedBrand);
                        showPriceFilterDialog(selectedBrand);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPriceFilterDialog(String selectedBrand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_price_filter, null);
        builder.setView(view);
        EditText editTextMinPrice = view.findViewById(R.id.editTextMinPrice);
        EditText editTextMaxPrice = view.findViewById(R.id.editTextMaxPrice);
        CheckBox anyPriceCheckbox = view.findViewById(R.id.anyPriceCheckbox);

        builder.setTitle("Фильтр по цене")
                .setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean anyPriceSelected = anyPriceCheckbox.isChecked();
                        if (anyPriceSelected) {
                            productAdapter.filterByBrand(selectedBrand);
                        } else {
                            String minPriceStr = editTextMinPrice.getText().toString().trim();
                            String maxPriceStr = editTextMaxPrice.getText().toString().trim();

                            if (!minPriceStr.isEmpty() && !maxPriceStr.isEmpty()) {
                                int minPrice = Integer.parseInt(minPriceStr);
                                int maxPrice = Integer.parseInt(maxPriceStr);
                                productAdapter.filterByBrandAndPrice(selectedBrand, minPrice, maxPrice);
                            } else {
                                productAdapter.filterByBrand(selectedBrand);
                            }
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
