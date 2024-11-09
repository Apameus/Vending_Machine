package vending.machine.serializers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vending.machine.data.AuthorizedUser;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthorizationSerializerTest {

    AuthorizationSerializer serializer = new AuthorizationSerializer();
    private final String VALID_LINE = "12345678,0000";
    private final AuthorizedUser VALID_USER = new AuthorizedUser(12345678,0000);

    @Test
    @DisplayName("Parse a user")
    void parseAUser() {
        AuthorizedUser user = serializer.parseUser(VALID_LINE);
        assertThat(user).isEqualTo(VALID_USER);
        assertEquals(user,VALID_USER);
    }

    @Test
    @DisplayName("Serialize a user")
    void serializeAUser() {
        String line = serializer.serialize(VALID_USER);
        assertThat(line).isEqualTo(line);
    }
}