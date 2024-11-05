package vending.machine.serializers;

import vending.machine.data.Product;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface AnalyticSerializer {

    Float parseTotalEarnings() throws IOException;
    void serializeTotalEarnings(Float totalEarnings) throws IOException;

    List<Sale> parseAllSales() throws IOException;
    Sale parseSale(String line);

    List<UserMovement> parseAllUserMovement() throws IOException;
    UserMovement parseUserMovement(String line);


    void serializeAllSales(List<Sale> saleCache);
    void serializeSale(Sale sale) throws IOException;

    void serializeAllUserMovement(HashMap<Integer,Integer> userMovementCache);
    void serializeUserMovement(UserMovement userMovement) throws IOException;
}
