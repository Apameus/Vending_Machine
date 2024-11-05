package vending.machine.serializers;

import vending.machine.data.AuthorizedUser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public interface AuthorizationSerializer {
    HashMap<Integer, AuthorizedUser> parseAllUsers(Path path) throws IOException;
    AuthorizedUser parse(String line);

//    void serializeAll(HashMap<Integer,AuthorizedUser> authorizedUserCache, Path path);
//    void serialize(AuthorizedUser authorizedUser, Path path);
}
