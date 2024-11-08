package vending.machine.serializers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductSerializerTest {

    private final Product PRODUCT = new Product(1, "banana", 13, 7);
    private final String VALID_LINE = "1,banana,13,7";
    private final ProductSerializer serializer = new ProductSerializer();

    @Test
    @DisplayName("ParseProduct")
    void parseProduct() {
        Product product = serializer.parse(VALID_LINE);
        assertThat(product).isEqualTo(PRODUCT);
    }

    @Test
    @DisplayName("SerializeProduct")
    void serializeProduct() {
        String line = serializer.serialize(PRODUCT);
        assertThat(line).isEqualTo(VALID_LINE);
    }

}