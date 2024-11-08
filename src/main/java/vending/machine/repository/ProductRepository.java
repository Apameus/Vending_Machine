package vending.machine.repository;

import vending.machine.data.Product;

import java.util.List;

public interface ProductRepository {
    Product findProductById(int productId);

    void saveProduct(Product product);

    List<Product> findAllProducts();
}
