package vending.machine.data;

public record Sale(int productId, int numberOfSales) {
    public Sale withNumberOfSales(int newSales){
        return new Sale(productId, newSales);
    }
}
