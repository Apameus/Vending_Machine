package vending.machine.repository.dataSource;

import vending.machine.data.Product;

import java.util.Collection;

public interface ProductDataSource {
    Collection<Product> load();

    void save(Collection<Product> products);
}
