package vending.machine.serializers;

import vending.machine.data.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public final class ProductSerializerImpl implements ProductSerializer{

    @Override
    public HashMap<Integer,Product> parseAllProducts(Path path) throws IOException {
        HashMap<Integer,Product> productMap = new HashMap<>();
        List<String> lines = Files.readAllLines(path);
        lines.forEach(line -> {
            Product product = parse(line);
            productMap.put(product.id(), product);
        });
        return productMap;
    }

    @Override
    public Product parse(String line) {
        String[] values = line.split(",");
        return new Product(Integer.parseInt(values[0]), values[1], Float.parseFloat(values[2]), Integer.parseInt(values[3]));
    }


    @Override
    public void serializeAll(HashMap<Integer,Product> productCache, Path path){
        productCache.forEach((integer, product) -> serialize(product, path));
    }


    @Override
    public void serialize(Product product, Path path) {
        String line = product.id() + "," + product.name() + product.price() + "," + product.quantity();
        try {
            Files.write(path, line.getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
