package vending.machine.serializers;

import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.io.IOException;

public interface AnalyticSerializer {

    Earnings parseEarnings(String line);

    String serializeEarnings(Earnings earnings);


    Sale parseSale(String line);
    String serializeSale(Sale sale);


    UserMovement parseUserMovement(String line);
    String serializeUserMovement(UserMovement userMovement);
}
