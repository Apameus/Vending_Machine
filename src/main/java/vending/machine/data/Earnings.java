package vending.machine.data;

public record Earnings(float totalEarnings, float availableEarnings) {
    public Earnings updateAvailableEarnings(float newAvailableEarnings){
        return new Earnings(totalEarnings, newAvailableEarnings);
    }
    public Earnings increaseEarningsBy(Float money){
        return new Earnings(totalEarnings + money, availableEarnings + money);
    }
}
