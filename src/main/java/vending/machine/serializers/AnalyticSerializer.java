package vending.machine.serializers;

import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.io.IOException;
import java.nio.file.Path;

public interface AnalyticSerializer {

    Float parseTotalEarnings(Path path) throws IOException;
    void serializeTotalEarnings(Float totalEarnings, Path path) throws IOException;


    Sale parseSale(String line);
    String serializeSale(Sale sale);


    UserMovement parseUserMovement(String line);
    String serializeUserMovement(UserMovement userMovement) throws IOException;
}
