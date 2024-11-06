package vending.machine.repositories;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;
import vending.machine.serializers.AnalyticSerializer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class AnalyticRepositoryImpl implements AnalyticRepository{
    private HashMap<Integer,Sale> salesCache;
    private HashMap<Integer,UserMovement> userMovements;
    private Float totalEarnings;
    private Float availableEarnings;//TODO calculate availableEarnings

    private AnalyticSerializer serializer;
    Path salesPath;
    Path totalEarningsPath;


    public AnalyticRepositoryImpl(Path salesPath, Path totalEarningsPath, AnalyticSerializer serializer) {
        this.serializer = serializer;
        this.salesPath = salesPath;
        this.totalEarningsPath = totalEarningsPath;
        try {
            salesCache = parseAllSales();
            totalEarnings = serializer.parseTotalEarnings(totalEarningsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseTotalEarningsBy(float price) {
        totalEarnings += price;
        try {
            serializer.serializeTotalEarnings(totalEarnings, totalEarningsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseSales(int productId) {
        Sale sale = salesCache.get(productId).increaseNumberOfSales();
        salesCache.put(productId, sale);
        serializeAllSales();
    }

    @Override
    public List<Sale> getAllSales() {
        return List.of();
    }

    @Override
    public Float totalEarnings() {
        return totalEarnings;
    }

    @Override
    public Float retrieveAvailableEarnings() {
        var previousAvailableEarnings = availableEarnings;
        availableEarnings = 0F;
        return previousAvailableEarnings;
    }

    @Override
    public void trackMoneyMovement(Integer userId, Float availableEarnings) {
        userMovements.put(userId, new UserMovement(userId, availableEarnings)); //TODO
        serializeAllUserMovements();
    }


    //sales
    public HashMap<Integer,Sale> parseAllSales() throws IOException {
        HashMap<Integer,Sale> sales = new HashMap<>();
        List<String> lines = Files.readAllLines(salesPath);
        lines.forEach(line -> {
            Sale sale = serializer.parseSale(line);
            sales.put(sale.productId(), sale);
        });
        return sales;
    }


    //sales
    public void serializeAllSales() {
        List<String> lines = new ArrayList<>();
        salesCache.forEach((productId, sale) -> {
            lines.add(serializer.serializeSale(sale));
        });
        try {
            Files.write(salesPath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeAllUserMovements(){
        List<String> lines = new ArrayList<>();
        userMovements.forEach((integer, userMovement) -> {
            try {
                lines.add(serializer.serializeUserMovement(userMovement));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
