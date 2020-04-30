package sjt.http.server.db;

/**
 * Created by yusik on 2020/04/29.
 */
public class InMemoryDb {
    private static final Table<User> USER_TABLE = new Table<>();

    public static User saveUser(User user) {
        System.out.println("## Save user: " + user);
        return USER_TABLE.insert(user);
    }

    public static User findUserById(String id) {
        System.out.println("## Find user: " + id);
        return USER_TABLE.select(id);
    }

    public static User updateUser(User user) {
        System.out.println("## Update user: " + user);
        return USER_TABLE.update(user);
    }

    public static User deleteUserById(String id) {
        System.out.println("## Delete user: " + id);
        return USER_TABLE.delete(id);
    }

}
