package vending.machine.serializers;

import vending.machine.data.Product;

import java.io.IOException;
import java.util.HashMap;

public interface ProductSerializer {

    HashMap<Integer,Product> parseAll() throws IOException;
    Product parse(String line);

    void serializeAll(HashMap<Integer,Product> productCache);
    void serialize(Product product);
}
