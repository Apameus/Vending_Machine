package vending.machine.serializers;

import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.io.IOException;
import java.nio.file.Path;

public interface AnalyticSerializer {

    Float parseTotalEarnings(Path path) throws IOException;
    void serializeTotalEarnings(Float totalEarnings, Path path) throws IOException;


//    HashMap<Integer,Sale> parseAllSales(Path path) throws IOException;
    Sale parseSale(String line);
//    void serializeAllSales(HashMap<Integer,Sale> saleCache, Path path);
    String serializeSale(Sale sale);


//    HashMap<Integer,UserMovement> parseAllUserMovement(Path path) throws IOException;
    UserMovement parseUserMovement(String line);
//    void serializeAllUserMovement(HashMap<Integer,Integer> userMovementCache, Path path);
    void serializeUserMovement(UserMovement userMovement, Path path) throws IOException;
}
