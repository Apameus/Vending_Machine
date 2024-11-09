package vending.machine.serializers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vending.machine.data.Sale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnalyticSerializerTest {
    AnalyticSerializer serializer = new AnalyticSerializer();

    final String SALE_VALID_LINE = "1,15";
    final Sale VALID_SALE = new Sale(1,15);

    @Test
    @DisplayName("Parse Sale")
    void parseSale() {
        Sale sale = serializer.parseSale(SALE_VALID_LINE);
        assertThat(sale).isEqualTo(VALID_SALE);
    }

    @Test
    @DisplayName("Serialize Sale")
    void serializeSale() {
        String line = serializer.serializeSale(VALID_SALE);
        assertThat(line).isEqualTo(SALE_VALID_LINE);
    }

}