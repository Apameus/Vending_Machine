package vending.machine.data;

public record Product(int id, String name, float price, int quantity) {
    public Product updateId(int newId){
        return new Product(newId, name, price, quantity);
    }

    public Product updateName(String newName){
        return new Product(id, newName, price, quantity);
    }

    public Product updatePrice(float newPrice){
        return new Product(id, name, newPrice, quantity);
    }

    public Product updateQuantity(int newQuantity){
        return new Product(id, name, price, newQuantity);
    }
}
