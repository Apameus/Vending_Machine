package vending.machine.serializers;
import vending.machine.data.Sale;
import vending.machine.data.UserMovement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AnalyticSerializerImpl implements AnalyticSerializer{


    @Override
    public Float parseTotalEarnings(Path path) throws IOException {
        String line = Files.readAllLines(Path.of("src/main/resources/TotalEarnings")).getFirst();
        return Float.valueOf(line);
    }

    @Override //todo: add path as parameter
    public void serializeTotalEarnings(Float totalEarnings, Path path) throws IOException {
        Files.write(path,totalEarnings.toString().getBytes());
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
    public void serializeUserMovement(UserMovement userMovement, Path path)   {
        String line = userMovement.userId() + "," + userMovement.receivedMoney();
        try {
            Files.write((path),line.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
