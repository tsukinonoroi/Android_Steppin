package com.example.steppin;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import Data.DatabaseHelper;
import Model.Product;
import okhttp3.OkHttpClient;

public class AdminActivity extends AppCompatActivity {
    EditText editTextProductName, editTextProductBrand, editTextProductColor, editTextProductSize, editTextProductPrice;
    Button buttonAddProduct, buttonChooseImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private byte[] selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductBrand = findViewById(R.id.editTextProductBrand);
        editTextProductColor = findViewById(R.id.editTextProductColor);
        editTextProductSize = findViewById(R.id.editTextProductSize);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToDataBase();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                byte[] imageBytes = getBytes(inputStream);
                selectedImage = imageBytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void addProductToDataBase() {
        String name = editTextProductName.getText().toString().trim();
        String brand = editTextProductBrand.getText().toString().trim();
        String color = editTextProductColor.getText().toString().trim();
        int size = Integer.parseInt(editTextProductSize.getText().toString().trim());
        int price = Integer.parseInt(editTextProductPrice.getText().toString().trim());

        Product product = new Product(name, brand, color, size, selectedImage, price);

        DatabaseHelper db = new DatabaseHelper(this);
        db.addProduct(product);

        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

        editTextProductName.setText("");
        editTextProductBrand.setText("");
        editTextProductColor.setText("");
        editTextProductSize.setText("");
        editTextProductPrice.setText("");
    }
}
