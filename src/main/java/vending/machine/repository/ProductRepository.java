package vending.machine.repository;

import vending.machine.data.Product;

import java.util.List;

public interface ProductRepository {
    Product findProductById(int productId);

    List<Product> findAllProducts();

    void saveProduct(Product product);

    void removeProduct(int productId);
}
