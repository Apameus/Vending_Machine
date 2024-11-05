package vending.machine.serializers;

import vending.machine.data.Product;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class AnalyticSerializerImpl implements AnalyticSerializer{


    @Override
    public Float parseTotalEarnings() throws IOException {
        String line = Files.readAllLines(Path.of("src/main/resources/TotalEarnings")).getFirst();
        return Float.valueOf(line);
    }

    @Override
    public void serializeTotalEarnings(Float totalEarnings) throws IOException {
        Files.write(Path.of("src/main/resources/TotalEarnings"),totalEarnings.toString().getBytes());
    }

    @Override
    public List<Sale> parseAllSales() throws IOException {
        List<Sale> sales = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/Sales"));
        lines.forEach(line -> sales.add(parseSale(line)));
        return sales;
    }

    @Override
    public Sale parseSale(String line) {
        String[] values = line.split(",");
        return new Sale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    @Override
    public List<UserMovement> parseAllUserMovement() throws IOException {
        List<UserMovement> movements = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/UserMovement"));
        lines.forEach(line -> movements.add(parseUserMovement(line)));
        return movements;
    }

    @Override
    public UserMovement parseUserMovement(String line) {
        String[] values = line.split(",");
        return new UserMovement(Integer.parseInt(values[0]), Float.parseFloat(values[1]));
    }

    @Override
    public void serializeAllSales(List<Sale> saleCache) {
        saleCache.forEach(this::serializeSale);
    }

    @Override
    public void serializeSale(Sale sale) {
        String line = sale.productId() + "," + sale.numberOfSales();
        try {
            Files.write(Path.of("src/main/resources/Sales"),line.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: REFACTOR
    @Override
    public void serializeAllUserMovement(HashMap<Integer, Integer> userMovementCache) {
        userMovementCache.forEach((userID, moneyReceived) -> serializeUserMovement(new UserMovement(userID, moneyReceived)));
    }

    @Override
    public void serializeUserMovement(UserMovement userMovement)   {
        String line = userMovement.userId() + "," + userMovement.receivedMoney();
        try {
            Files.write(Path.of("src/main/resources/Sales"),line.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
