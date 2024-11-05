package vending.machine.serializers;

import vending.machine.data.AuthorizedUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public final class AuthorizationSerializerImpl implements AuthorizationSerializer{
    @Override
    public HashMap<Integer, AuthorizedUser> parseAllUsers(Path path) throws IOException {
        HashMap<Integer,AuthorizedUser> userMap = new HashMap<>();
        List<String> lines = Files.readAllLines(path);
        lines.forEach(line -> {
            AuthorizedUser user = parse(line);
            userMap.put(user.userId(), user);
        });
        return userMap;
    }

    @Override
    public AuthorizedUser parse(String line) {
        String[] values = line.split(",");
        return new AuthorizedUser(values[0], Integer.parseInt(values[1]), values[2]);
    }

    @Override
    public void serializeAll(HashMap<Integer, AuthorizedUser> authorizedUserCache, Path path) {
        authorizedUserCache.forEach((integer, user) -> serialize(user, path));
    }

    @Override
    public void serialize(AuthorizedUser authorizedUser, Path path) {
        String line = authorizedUser.name() + "," + authorizedUser.userId() + ',' + authorizedUser.password();
        try {
            Files.write(path, line.getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
