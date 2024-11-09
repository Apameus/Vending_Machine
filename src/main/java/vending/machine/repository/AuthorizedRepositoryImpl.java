package vending.machine.repository;

import vending.machine.data.AuthorizedUser;

import java.util.HashMap;

public final class AuthorizedRepositoryImpl implements AuthorizedRepository {

    private HashMap<Integer,AuthorizedUser> usersCache;

    @Override
    public AuthorizedUser findUserById(int userId) {
        return null;
    }
}
