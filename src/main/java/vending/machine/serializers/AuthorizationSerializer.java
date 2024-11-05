package vending.machine.serializers;

import vending.machine.data.AuthorizedUser;
import vending.machine.data.Product;

import java.io.IOException;
import java.util.HashMap;

public interface AuthorizationSerializer {
    HashMap<Integer, AuthorizedUser> parseAll() throws IOException;
    AuthorizedUser parse(String line);

    void serializeAll(HashMap<Integer,AuthorizedUser> authorizedUserCache);
    void serialize(AuthorizedUser authorizedUser);
}
