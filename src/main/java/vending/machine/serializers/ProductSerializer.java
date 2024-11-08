package vending.machine.serializers;

import vending.machine.data.Product;

public class ProductSerializer {
    public Product parse(String line) {
        String[] values = line.split(",");
        return new Product(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
    }

    public String serialize(Product product) {
        return product.id() + "," + product.name() + "," + product.price() + "," + product.quantity();
    }
}
