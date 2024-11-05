package vending.machine.serializers;

import vending.machine.data.Product;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductSerializer {

    HashMap<Integer,Product> parseAll() throws IOException;
    Product parse(String line);

    void serializeAll(HashMap<Integer,Product> productList);
    void serialize(Product product);
}
