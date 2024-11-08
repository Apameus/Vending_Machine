package vending.machine.repository;

import vending.machine.data.Sale;
import vending.machine.repository.dataSource.AnalyticsDataSource;
import vending.machine.repository.dataSource.ProductDataSource;

import java.util.HashMap;
import java.util.List;

public final class AnalyticsRepositoryImpl implements AnalyticsRepository {

                // Product.id, Sale
    private HashMap<Integer, Sale> salesCache;
    //TODO: Earnings
    //TODO: UserMovement
    AnalyticsDataSource dataSource;

    public AnalyticsRepositoryImpl(AnalyticsDataSource dataSource) {
        this.dataSource = dataSource;
        salesCache = new HashMap<>();
        dataSource.load().forEach(sale -> salesCache.put(sale.productId(), sale));
    }

    @Override
    public void increaseSales(int productId) {
        Sale sale = salesCache.get(productId);
        salesCache.put(sale.productId(), sale.withNumberOfSales(sale.numberOfSales() + 1));
        dataSource.save(List.copyOf(salesCache.values()));
    }

    @Override
    public void increaseEarnings(int price) {

    }
}
