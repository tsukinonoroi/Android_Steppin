package Model;

import java.util.HashSet;

public class Product {
    private int id;
    private String name;

    private String brand;
    private String color;
    private int size;
    private byte[] image;

    private int price;

    public Product() {
    }


    public Product(String name, String brand, String color, int size, byte[] image, int price) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.image = image;
        this.price = price;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
