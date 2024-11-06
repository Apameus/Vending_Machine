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
        return new AuthorizedUser(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
    }

}
