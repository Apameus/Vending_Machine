package vending.machine.serializers;

import vending.machine.data.Product;

public interface ProductSerializer {
    Product parse(String line);
    void serialize(Product product);
}
