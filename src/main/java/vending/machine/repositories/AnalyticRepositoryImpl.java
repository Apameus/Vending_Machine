package vending.machine.repositories;

import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.util.HashMap;
import java.util.List;

public final class AnalyticRepositoryImpl implements AnalyticRepository{
    private HashMap<Integer,Sale> sales;
    Float totalEarnings;


    public AnalyticRepositoryImpl(HashMap<Integer,Sale> sales, Float totalEarnings) {
        this.sales = sales;
        this.totalEarnings = totalEarnings;
    }

    //TODO: SERIALIZE
    @Override
    public void increaseTotalEarningsBy(float price) {
        totalEarnings += price;
    }

    //TODO: SERIALIZE
    @Override
    public void increaseSales(int productId) {
        sales.get(productId).increaseNumberOfSales();
    }
}
