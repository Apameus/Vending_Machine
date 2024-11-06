package vending.machine.repositories;

import vending.machine.data.Product;

import java.util.Collection;

public interface ProductRepository {
    Collection<Product> findAllProducts();

    Product findProductBy(int productId);

    void decreaseQuantity(int productId);

    void updateQuantity(int productId, Integer quantity);

    void updatePrice(int productId, Float price);

    void updateId(int product, Integer newId);

    void updateName(int productId, String name);

    void addProduct(Product product);

    void removeProduct(int productId);
}
