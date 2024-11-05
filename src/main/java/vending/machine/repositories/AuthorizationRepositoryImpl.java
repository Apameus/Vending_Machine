package vending.machine.repositories;
import vending.machine.data.AuthorizedUser;
import vending.machine.data.UserMovement;
import java.util.HashMap;

public final class AuthorizationRepositoryImpl implements AuthorizationRepository{
    private final HashMap<Integer,AuthorizedUser> authorizedUsersCache;
    private HashMap<Integer,UserMovement> userMovements; //TODO: is this the right place?

    public AuthorizationRepositoryImpl(HashMap<Integer, AuthorizedUser> authorizedUsersCache) {
        this.authorizedUsersCache = authorizedUsersCache;
    }

    @Override
    public AuthorizedUser findUserBy(int userId) {
        return authorizedUsersCache.get(userId);
    }
}
