public class Producto {
    private int reference;
    private String name;
    private int stock;
    private int category;
    private double price;
    
    public Producto(int reference, String name, int stock, int category, double price) {
        this.reference = reference;
        this.name = name;
        this.stock = stock;
        this.category = category;
        this.price = price;
    }
    public int getReference() {return reference;}
    public void setReference(int reference) {this.reference = reference;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public int getCategory() {return category;}
    public void setCategory(int category) {this.category = category;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public void addStock(int quantity){ this.stock += quantity;}
    public void reduceStock(int quantity){ this.stock -= quantity;}

    @Override
    public String toString(){
        return reference + " - " + name + " - " + stock + " - " + price;
    }

}
