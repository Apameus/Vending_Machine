package vending.machine.serializers;

import vending.machine.data.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ProductSerializerImpl implements ProductSerializer{

    public List<Product> parseAll() throws IOException {
        List<Product> productList = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/Products.txt"));
        lines.forEach(line -> productList.add(parse(line)));
        return productList;
    }

    public void serializeAll(List<Product> productList){
        productList.forEach(this::serialize);
    }


    @Override
    public Product parse(String line) {
        String[] values = line.split(",");
        return new Product(Integer.parseInt(values[0]), values[1], Float.parseFloat(values[2]), Integer.parseInt(values[3]));
    }

    @Override
    public void serialize(Product product) {
        String line = product.id() + "," + product.name() + product.price() + "," + product.quantity();
        try {
            Path path = Path.of("src/main/resources/Products.txt");
            Files.write(path, line.getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
