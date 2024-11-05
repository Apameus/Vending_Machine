package vending.machine.serializers;

import vending.machine.data.AuthorizedUser;

import java.io.IOException;
import java.util.HashMap;

public interface AuthorizationSerializer {
    HashMap<Integer, AuthorizedUser> parseAllUsers() throws IOException;
    AuthorizedUser parse(String line);

    void serializeAll(HashMap<Integer,AuthorizedUser> authorizedUserCache);
    void serialize(AuthorizedUser authorizedUser);
}
