package vending.machine.repositories;
import vending.machine.data.Earnings;
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
    private final HashMap<Integer,Sale> salesCache;
    private final HashMap<Integer,UserMovement> userMovementsCache;
    private Earnings earnings;

    private final AnalyticSerializer serializer;
    private final Path salesPath;
    private final Path earningsPath;
    private final Path userMovementsPath;


    public AnalyticRepositoryImpl(Path salesPath, Path earningsPath, Path userMovementsPath, AnalyticSerializer serializer) {
        this.serializer = serializer;
        this.salesPath = salesPath;
        this.earningsPath = earningsPath;
        this.userMovementsPath = userMovementsPath;
        try {
            salesCache = parseAllSales();
            earnings = parseEarnings();
            userMovementsCache = parseUserAllMovements();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseEarningsBy(float price) {
       earnings = earnings.increaseEarningsBy(price);
       serializeEarnings();
    }

    @Override
    public void increaseSales(int productId) {
        Sale sale = salesCache.get(productId).increaseNumberOfSales();
        salesCache.put(productId, sale);
        serializeAllSales();
    }

    @Override
    public List<Sale> getAllSales() {
        return new ArrayList<>(salesCache.values()); //todo refactor?
    }

    @Override
    public Float totalEarnings() {
        return earnings.totalEarnings();
    }

    @Override
    public Float retrieveAvailableEarnings() {
        return earnings.availableEarnings();
    }

    public void resetAvailableEarnings(){
        earnings = earnings.updateAvailableEarnings(0);
        serializeEarnings();
    }

    @Override
    public void trackMoneyMovement(Integer userId, Float availableEarnings) {
        float alreadyReceivedMoney = userMovementsCache.get(userId).receivedMoney();
        userMovementsCache.put(userId, new UserMovement(userId, alreadyReceivedMoney + availableEarnings));
        serializeAllUserMovements();
    }


    //sales
    private HashMap<Integer,UserMovement> parseUserAllMovements() throws IOException {
        HashMap<Integer,UserMovement> moneyMovements = new HashMap<>();
        List<String> lines = Files.readAllLines(userMovementsPath);
        lines.forEach(line -> {
            UserMovement userMovement = serializer.parseUserMovement(line);
            moneyMovements.put(userMovement.userId(), userMovement);
        });
        return moneyMovements;
    }

    public HashMap<Integer,Sale> parseAllSales() throws IOException {
        HashMap<Integer,Sale> sales = new HashMap<>();
        List<String> lines = Files.readAllLines(salesPath);
        lines.forEach(line -> {
            Sale sale = serializer.parseSale(line);
            sales.put(sale.productId(), sale);
        });
        return sales;
    }

    private void serializeEarnings(){
        String line = serializer.serializeEarnings(earnings);
        try {
            Files.write(earningsPath, line.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Earnings parseEarnings(){
        try {
            String line = Files.readAllLines(earningsPath).getFirst();
            return serializer.parseEarnings(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //sales
    public void serializeAllSales() {
        List<String> lines = new ArrayList<>();
//        salesCache.values().stream()
//                .map(serializer::serializeSale)
//                .forEach(lines::add);
        salesCache.forEach((_, sale) -> lines.add(serializer.serializeSale(sale)));
        try {
            Files.write(salesPath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeAllUserMovements(){
        List<String> lines = new ArrayList<>();
        userMovementsCache.forEach((_, userMovement) -> {
            lines.add(serializer.serializeUserMovement(userMovement));
            try {
                Files.write(userMovementsPath, lines);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
