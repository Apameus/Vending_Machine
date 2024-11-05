package vending.machine.data;

public record Sale(int productId, int numberOfSales) {
    public Sale increaseNumberOfSales(){
        return new Sale(productId, numberOfSales+1);
    }
}
