package sjt.http.server.db;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yusik on 2020/04/29.
 */
@Slf4j
public class InMemoryDb {
    private static final Table<User> USER_TABLE = new Table<>();

    public static User saveUser(User user) {
        log.debug("## Save user: {}", user);
        return USER_TABLE.insert(user);
    }

    public static User findUserById(String id) {
        log.debug("## Find user: {}", id);
        return USER_TABLE.select(id);
    }

    public static User updateUser(User user) {
        log.debug("## Update user: {}", user);
        return USER_TABLE.update(user);
    }

    public static User deleteUserById(String id) {
        log.debug("## Delete user: {}", id);
        return USER_TABLE.delete(id);
    }

}
