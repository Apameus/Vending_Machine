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
    public HashMap<Integer,Sale> parseAllSales() throws IOException {
        HashMap<Integer,Sale> sales = new HashMap<>();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/Sales"));
        lines.forEach(line -> {
            Sale sale = parseSale(line);
            sales.put(sale.productId(), sale);
        });
        return sales;
    }

    @Override
    public Sale parseSale(String line) {
        String[] values = line.split(",");
        return new Sale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    @Override
    public HashMap<Integer,UserMovement> parseAllUserMovement() throws IOException {
        HashMap<Integer, UserMovement> movements = new HashMap<>();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/UserMovement"));
        lines.forEach(line -> {
            UserMovement userMovement = parseUserMovement(line);
            movements.put(userMovement.userId(), userMovement);
        });
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
