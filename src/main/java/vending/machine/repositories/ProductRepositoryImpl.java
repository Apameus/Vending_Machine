package vending.machine.repositories;

import vending.machine.data.Product;

import java.util.Collection;
import java.util.HashMap;

public final class ProductRepositoryImpl implements ProductRepository{
    private final HashMap<Integer, Product> productCache;

    public ProductRepositoryImpl(HashMap<Integer, Product> productCache) {
        this.productCache = productCache;
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
    }


}
