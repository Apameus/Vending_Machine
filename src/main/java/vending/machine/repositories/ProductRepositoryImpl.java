package vending.machine.repositories;

import vending.machine.data.Product;
import vending.machine.serializers.ProductSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class ProductRepositoryImpl implements ProductRepository{
    private final HashMap<Integer, Product> productCache;
    private final ProductSerializer serializer;
    private final Path path;

    public ProductRepositoryImpl(Path path, ProductSerializer serializer) {
        this.serializer = serializer;
        this.path = path;
        try {
            productCache = parseAllProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Product> findAllProducts(){
        return productCache.values();
    }

    @Override
    public Product findProductBy(int productId) {
        return productCache.get(productId);
    }

    //TODO: REFACTOR
    @Override
    public void decreaseQuantity(int productId) {
        Product product = productCache.get(productId);
        Product productWithDecreasedQuantity = productCache.get(productId).updateQuantity(product.quantity() - 1);
        productCache.put(productId, productWithDecreasedQuantity);
        serializeAll();
    }


    //TODO: parseAll
    public HashMap<Integer,Product> parseAllProducts() throws IOException {
        HashMap<Integer,Product> productMap = new HashMap<>();
        List<String> lines = Files.readAllLines(path);
        lines.forEach(line -> {
            Product product = serializer.parse(line);
            productMap.put(product.id(), product);
        });
        return productMap;
    }


    //TODO: serializeAll
    public void serializeAll(){
        List<String> lines = new ArrayList<>();
        productCache.forEach((integer, product) -> lines.add(serializer.serialize(product)));
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
