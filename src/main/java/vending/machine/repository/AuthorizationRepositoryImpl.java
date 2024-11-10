package vending.machine.repository;

import vending.machine.data.AuthorizedUser;
import vending.machine.repository.dataSource.AuthorizationDataSource;

import java.util.HashMap;

public final class AuthorizationRepositoryImpl implements AuthorizationRepository {

    private HashMap<Integer,AuthorizedUser> usersCache;
    private AuthorizationDataSource dataSource;

    public AuthorizationRepositoryImpl(AuthorizationDataSource dataSource) {
        this.dataSource = dataSource;
        usersCache = new HashMap<>();
        dataSource.load().forEach(user -> usersCache.put(user.id(), user));
    }

    @Override
    public AuthorizedUser findUserById(int userId) {
        return usersCache.get(userId);
    }
}
