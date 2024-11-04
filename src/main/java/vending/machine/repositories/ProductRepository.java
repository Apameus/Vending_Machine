package vending.machine.repositories;

import vending.machine.data.Product;

import java.util.Collection;

public interface ProductRepository {
    Collection<Product> findAllProducts();

    Product findProductBy(int productId);

    void decreaseQuantity(int productId);
}
