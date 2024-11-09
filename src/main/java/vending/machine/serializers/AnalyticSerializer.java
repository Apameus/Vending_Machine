package vending.machine.serializers;

import vending.machine.data.Earnings;
import vending.machine.data.Sale;

public final class AnalyticSerializer {

    public Sale parseSale(String line){
        String[] values = line.split(",");
        return new Sale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }
    public String serializeSale(Sale sale){
        return sale.productId() + "," + sale.numberOfSales();
    }

    public Earnings parseEarnings(String line){
        String[] values = line.split(",");
        return new Earnings(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    public String serializeEarning(Earnings earnings){
        return earnings.totalEarnings() + "," + earnings.availableEarnings();
    }
}
