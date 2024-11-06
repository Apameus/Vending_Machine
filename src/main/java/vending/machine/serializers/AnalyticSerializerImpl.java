package vending.machine.serializers;
import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AnalyticSerializerImpl implements AnalyticSerializer{


    @Override
    public Earnings parseEarnings(String line)  {
        String[] values = line.split(",");
        return new Earnings(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
    }

    @Override
    public String serializeEarnings(Earnings earnings) {
        return earnings.totalEarnings() + "," + earnings.availableEarnings();
    }



    @Override
    public Sale parseSale(String line) {
        String[] values = line.split(",");
        return new Sale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }


    @Override
    public UserMovement parseUserMovement(String line) {
        String[] values = line.split(",");
        return new UserMovement(Integer.parseInt(values[0]), Float.parseFloat(values[1]));
    }



    @Override
    public String serializeSale(Sale sale) {
        return  sale.productId() + "," + sale.numberOfSales();
    }


    @Override
    public String serializeUserMovement(UserMovement userMovement)   {
        return userMovement.userId() + "," + userMovement.receivedMoney();
    }
}
