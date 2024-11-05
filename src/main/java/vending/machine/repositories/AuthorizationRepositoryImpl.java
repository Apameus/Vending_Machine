package vending.machine.repositories;

import vending.machine.data.AuthorizedUser;

import java.util.HashMap;

public final class AuthorizationRepositoryImpl implements AuthorizationRepository{
    private final HashMap<Integer,AuthorizedUser> authorizedUsersCache;

    public AuthorizationRepositoryImpl(HashMap<Integer, AuthorizedUser> authorizedUsersCache) {
        this.authorizedUsersCache = authorizedUsersCache;
    }

    @Override
    public AuthorizedUser findUserBy(int userId) {
        return authorizedUsersCache.get(userId);
    }
}
