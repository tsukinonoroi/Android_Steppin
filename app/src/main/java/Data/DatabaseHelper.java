package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Product;
import Model.User;
import Utils.Util;
import Utils.UtilProduct;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_EMAIL + " TEXT, "
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + UtilProduct.TABLE_NAME + " ("
                + UtilProduct.KEY_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UtilProduct.KEY_NAME_PRODUCT + " TEXT, "
                + UtilProduct.KEY_BRAND_PRODUCT + " TEXT, "
                + UtilProduct.KEY_COLOR_PRODUCT + " TEXT, "
                + UtilProduct.KEY_SIZE_PRODUCT + " INTEGER, "
                + UtilProduct.KEY_PRICE_PRODUCT + " INTEGER, "
                + UtilProduct.KEY_IMG_PRODUCT + " BLOB" + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UtilProduct.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;

        String selectQuery = "SELECT * FROM " + UtilProduct.TABLE_NAME + " WHERE " + UtilProduct.KEY_ID_PRODUCT + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            product = new Product();
            int idIndex = cursor.getColumnIndex(UtilProduct.KEY_ID_PRODUCT);
            if (idIndex >= 0) {
                product.setId(cursor.getInt(idIndex));
            }

            int nameIndex = cursor.getColumnIndex(UtilProduct.KEY_NAME_PRODUCT);
            if (nameIndex >= 0) {
                product.setName(cursor.getString(nameIndex));
            }

            int brandIndex = cursor.getColumnIndex(UtilProduct.KEY_BRAND_PRODUCT);
            if (brandIndex >= 0) {
                product.setBrand(cursor.getString(brandIndex));
            }

            int colorIndex = cursor.getColumnIndex(UtilProduct.KEY_COLOR_PRODUCT);
            if (colorIndex >= 0) {
                product.setColor(cursor.getString(colorIndex));
            }

            int sizeIndex = cursor.getColumnIndex(UtilProduct.KEY_SIZE_PRODUCT);
            if (sizeIndex >= 0) {
                product.setSize(cursor.getInt(sizeIndex));
            }

            int priceIndex = cursor.getColumnIndex(UtilProduct.KEY_PRICE_PRODUCT);
            if (priceIndex >= 0) {
                product.setPrice(cursor.getInt(priceIndex));
            }

            int imageIndex = cursor.getColumnIndex(UtilProduct.KEY_IMG_PRODUCT);
            if (imageIndex >= 0) {
                product.setImage(cursor.getBlob(imageIndex));
            }
        }

        cursor.close();
        db.close();

        return product;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public List<String> getAllBrands() {
        List<String> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT brand FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                String brand = cursor.getString(cursor.getColumnIndex("brand"));
                brands.add(brand);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return brands;
    }

    public List<Product> getProductsByBrand(String brand) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UtilProduct.TABLE_NAME + " WHERE " + UtilProduct.KEY_BRAND_PRODUCT + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{brand});

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                int idIndex = cursor.getColumnIndex(UtilProduct.KEY_ID_PRODUCT);
                if (idIndex != -1) {
                    product.setId(cursor.getInt(idIndex));
                }
                int nameIndex = cursor.getColumnIndex(UtilProduct.KEY_NAME_PRODUCT);
                if (nameIndex != -1) {
                    product.setName(cursor.getString(nameIndex));
                }
                int brandIndex = cursor.getColumnIndex(UtilProduct.KEY_BRAND_PRODUCT);
                if (brandIndex != -1) {
                    product.setBrand(cursor.getString(brandIndex));
                }
                int colorIndex = cursor.getColumnIndex(UtilProduct.KEY_COLOR_PRODUCT);
                if (colorIndex != -1) {
                    product.setColor(cursor.getString(colorIndex));
                }
                int sizeIndex = cursor.getColumnIndex(UtilProduct.KEY_SIZE_PRODUCT);
                if (sizeIndex != -1) {
                    product.setSize(cursor.getInt(sizeIndex));
                }
                int priceIndex = cursor.getColumnIndex(UtilProduct.KEY_PRICE_PRODUCT);
                if (priceIndex != -1) {
                    product.setPrice(cursor.getInt(priceIndex));
                }
                int imageIndex = cursor.getColumnIndex(UtilProduct.KEY_IMG_PRODUCT);
                if (imageIndex != -1) {
                    product.setImage(cursor.getBlob(imageIndex));
                }
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_EMAIL, user.getEmail());
        values.put(Util.KEY_NAME, user.getName());
        values.put(Util.KEY_PASSWORD, user.getPassword());
        db.insert(Util.TABLE_NAME, null, values);
        db.close();
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilProduct.KEY_NAME_PRODUCT, product.getName());
        values.put(UtilProduct.KEY_BRAND_PRODUCT, product.getBrand());
        values.put(UtilProduct.KEY_COLOR_PRODUCT, product.getColor());
        values.put(UtilProduct.KEY_SIZE_PRODUCT, product.getSize());
        values.put(UtilProduct.KEY_IMG_PRODUCT, product.getImage());
        values.put(UtilProduct.KEY_PRICE_PRODUCT, product.getPrice());

        db.insert(UtilProduct.TABLE_NAME, null, values);

        db.close();
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Util.KEY_ID};
        String selection = Util.KEY_EMAIL + " = ? AND " + Util.KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(Util.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public void updateUserName(int userId, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, newName);

        db.update(Util.TABLE_NAME, values, Util.KEY_ID + " = ?",
                new String[]{String.valueOf(userId)});
        db.close();
    }

    public void updateUserEmail(int userId, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_EMAIL, newEmail);

        db.update(Util.TABLE_NAME, values, Util.KEY_ID + " = ?",
                new String[]{String.valueOf(userId)});
        db.close();
    }

    public void updateUserPassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_PASSWORD, newPassword);

        db.update(Util.TABLE_NAME, values, Util.KEY_ID + " = ?",
                new String[]{String.valueOf(userId)});
        db.close();
    }
    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {Util.KEY_ID, Util.KEY_EMAIL, Util.KEY_NAME, Util.KEY_PASSWORD};
        String selection = Util.KEY_EMAIL + " = ? AND " + Util.KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(Util.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Util.KEY_ID);
            int emailIndex = cursor.getColumnIndex(Util.KEY_EMAIL);
            int nameIndex = cursor.getColumnIndex(Util.KEY_NAME);
            int passwordIndex = cursor.getColumnIndex(Util.KEY_PASSWORD);

            if (idIndex != -1 && emailIndex != -1 && nameIndex != -1 && passwordIndex != -1) {
                user = new User();
                user.setId(cursor.getInt(idIndex));
                user.setEmail(cursor.getString(emailIndex));
                user.setName(cursor.getString(nameIndex));
                user.setPassword(cursor.getString(passwordIndex));
            }
        }

        cursor.close();
        db.close();

        return user;
    }

    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Util.KEY_ID};
        String selection = Util.KEY_EMAIL + " = ? ";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(Util.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + UtilProduct.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                int idIndex = cursor.getColumnIndex(UtilProduct.KEY_ID_PRODUCT);
                if (idIndex != -1) {
                    product.setId(cursor.getInt(idIndex));
                }
                int nameIndex = cursor.getColumnIndex(UtilProduct.KEY_NAME_PRODUCT);
                if (nameIndex != -1) {
                    product.setName(cursor.getString(nameIndex));
                }
                int brandIndex = cursor.getColumnIndex(UtilProduct.KEY_BRAND_PRODUCT);
                if (brandIndex != -1) {
                    product.setBrand(cursor.getString(brandIndex));
                }
                int colorIndex = cursor.getColumnIndex(UtilProduct.KEY_COLOR_PRODUCT);
                if (colorIndex != -1) {
                    product.setColor(cursor.getString(colorIndex));
                }
                int sizeIndex = cursor.getColumnIndex(UtilProduct.KEY_SIZE_PRODUCT);
                if (sizeIndex != -1) {
                    product.setSize(cursor.getInt(sizeIndex));
                }
                int priceIndex = cursor.getColumnIndex(UtilProduct.KEY_PRICE_PRODUCT);
                if (priceIndex != -1) {
                    product.setPrice(cursor.getInt(priceIndex));
                }
                 int imageIndex = cursor.getColumnIndex(UtilProduct.KEY_IMG_PRODUCT);
                 if (imageIndex != -1) {
                     product.setImage(cursor.getBlob(imageIndex));
                 }
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }


}
