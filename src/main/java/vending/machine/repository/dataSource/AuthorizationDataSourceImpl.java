package vending.machine.repository.dataSource;

import vending.machine.data.AuthorizedUser;
import vending.machine.serializers.AuthorizationSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AuthorizationDataSourceImpl implements AuthorizationDataSource {

    private final Path path;
    private final AuthorizationSerializer serializer;

    public AuthorizationDataSourceImpl(Path path, AuthorizationSerializer serializer) {
        this.path = path;
        this.serializer = serializer;
    }

    @Override
    public Collection<AuthorizedUser> load() {
        try {
            List<AuthorizedUser> users = new ArrayList<>();
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> users.add(serializer.parseUser(line)));
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Collection<AuthorizedUser> userCache) {

    }
}
