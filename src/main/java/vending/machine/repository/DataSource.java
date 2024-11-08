package vending.machine.repository;

import vending.machine.data.Product;

import java.util.Collection;
import java.util.List;

public interface DataSource {
    Collection<Product> load();

    void save(Collection<Product> products);
}
