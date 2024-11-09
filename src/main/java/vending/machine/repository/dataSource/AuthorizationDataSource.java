package vending.machine.repository.dataSource;

import vending.machine.data.AuthorizedUser;

import java.util.Collection;

public interface AuthorizationDataSource {

    Collection<AuthorizedUser> load();
    void save(Collection<AuthorizedUser> userCache);
}
