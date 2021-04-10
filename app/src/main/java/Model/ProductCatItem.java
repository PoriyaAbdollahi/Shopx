package Model;

import android.widget.ImageView;

public class ProductCatItem {
    int id;
    String productImage;
    int price;
    String name;

    public ProductCatItem(String productImage, String name, int price) {
        this.price = price;
        this.productImage = productImage;
        this.name = name;


    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
