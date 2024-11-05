package vending.machine.data;

public record Sale(int productId, int numberOfSales) {
    public void increaseNumberOfSales(){
        new Sale(productId, numberOfSales + 1);
    }
}
