package Model;

public class BasketItem {
    private int price ;
    private String img ;

    public BasketItem(int price, String img) {
        this.price = price;
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
