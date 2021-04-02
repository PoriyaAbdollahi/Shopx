package Model;

public class Product {
    String name;
    int price;
    String imageAddress;
    String description;

    public Product(String name, int price, String imageAddress, String description) {
        this.name = name;
        this.price = price;
        this.imageAddress = imageAddress;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
