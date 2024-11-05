package vending.machine.serializers;

import vending.machine.data.Product;

import java.io.IOException;
import java.nio.file.Files;

public final class ProductSerializerImpl implements ProductSerializer{

    @Override
    public Product parse(String line) {
        String[] values = line.split(",");
        return new Product(Integer.parseInt(values[0]), values[1], Float.parseFloat(values[2]), Integer.parseInt(values[3]));
    }

    @Override
    public String serialize(Product product) {
        return product.id() + "," + product.name() + "," + product.price() + "," + product.quantity();
    }
}
