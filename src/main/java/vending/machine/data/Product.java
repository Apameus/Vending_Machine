package vending.machine.data;

import java.util.function.IntUnaryOperator;

public record Product(int id, String name, int price, int quantity) {

    public Product withId(int newId){
        return new Product(newId, name, price, quantity);
    }

    public Product withName(String newName){
        return new Product(id, newName, price, quantity);
    }

    public Product withPrice(int newPrice){
        return new Product(id, name, newPrice, quantity);
    }

    public Product withQuantity(int newQuantity){
        return new Product(id, name, price, newQuantity);
    }

    public Product withQuantity(IntUnaryOperator operator){
        return withQuantity(operator.applyAsInt(quantity));
    }
}
