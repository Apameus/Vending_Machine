package vending.machine.repositories;

import vending.machine.data.Product;
import vending.machine.serializers.ProductSerializer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;

public final class ProductRepositoryImpl implements ProductRepository{
    private final HashMap<Integer, Product> productCache;
    private final ProductSerializer serializer;
    private final Path path;

    public ProductRepositoryImpl(Path path, ProductSerializer serializer) {
        this.serializer = serializer;
        this.path = path;
        try {
            productCache = serializer.parseAllProducts(path);
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
        serializer.serializeAll(productCache, path);
    }


}
