package vending.machine.repository;

import vending.machine.data.Product;
import vending.machine.repository.dataSource.ProductDataSource;

import java.util.HashMap;
import java.util.List;

public final class ProductRepositoryImpl implements ProductRepository {
    private final HashMap<Integer,Product> productCache; // CACHE
    private final ProductDataSource dataSource;

    public ProductRepositoryImpl(ProductDataSource dataSource) {
        this.dataSource = dataSource;
        productCache = new HashMap<>();
        dataSource.load().forEach(product -> productCache.put(product.id(), product));
    }

    @Override
    public Product findProductById(int productId) {
        return productCache.get(productId);
    }

    @Override
    public void saveProduct(Product product) {
        productCache.put(product.id(), product);
        dataSource.save(List.copyOf(productCache.values()));
    }

    @Override
    public List<Product> findAllProducts() {
        return List.copyOf(productCache.values());
    }
}
