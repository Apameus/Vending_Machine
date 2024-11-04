package vending.machine.serializers;

import vending.machine.data.Product;

import java.util.List;

public interface AnalyticSerializer {
    List<Product> parseTopThreeProducts(List<String> lines);
}
