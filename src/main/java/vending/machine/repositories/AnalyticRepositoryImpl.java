package vending.machine.repositories;

import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.util.List;

public final class AnalyticRepositoryImpl implements AnalyticRepository{
    List<Sale> sales;
    List<UserMovement> userMovements;

    public AnalyticRepositoryImpl(List<Sale> sales, List<UserMovement> userMovements) {
        this.sales = sales;
        this.userMovements = userMovements;
    }

    @Override
    public void increaseTotalEarningsBy(float price) {

    }

    @Override
    public void increaseSales(int productId) {

    }
}
