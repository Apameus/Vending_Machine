package vending.machine.repositories;
import vending.machine.data.AuthorizedUser;
import vending.machine.data.UserMovement;
import vending.machine.serializers.AuthorizationSerializer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public final class AuthorizationRepositoryImpl implements AuthorizationRepository{
    private final HashMap<Integer,AuthorizedUser> authorizedUsersCache;


    public AuthorizationRepositoryImpl(Path path, AuthorizationSerializer serializer) {
        try {
            this.authorizedUsersCache = serializer.parseAllUsers(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthorizedUser findUserBy(int userId) {
        return authorizedUsersCache.get(userId);
    }
}
