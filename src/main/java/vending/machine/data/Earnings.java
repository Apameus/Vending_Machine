package vending.machine.data;

public record Earnings(int totalEarnings, int availableEarnings) {
    public Earnings increasedBy(int price){
        return new Earnings(totalEarnings + price, availableEarnings + price);
    }
}
