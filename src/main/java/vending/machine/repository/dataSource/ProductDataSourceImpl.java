package vending.machine.repository.dataSource;

import vending.machine.data.Product;
import vending.machine.serializers.ProductSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ProductDataSourceImpl implements ProductDataSource {

    private final Path path;
    private final ProductSerializer serializer;

    public ProductDataSourceImpl(Path path, ProductSerializer serializer) {
        this.path = path;
        this.serializer = serializer;
    }

    @Override
    public Collection<Product> load() {
        try {
            List<Product> products = new ArrayList<>();
            Files.readAllLines(path).forEach(line -> products.add(serializer.parse(line)));
//            Files.lines(path).map(serializer::parse).toList()
            return products;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Collection<Product> products) {
        List<String> lines = new ArrayList<>();
        products.forEach(product -> lines.add(serializer.serialize(product)));
        try {
            Files.write(path,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
