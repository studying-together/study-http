package sjt.http.client;


import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by kohyusik on 2020/03/04.
 */
class WebTest {

    public static void main(String[] args) {

        String username = "null";
        String result1 = Optional.ofNullable(username)
                .orElse(getDefaultName());
        System.out.println(result1);

        String result2 = Optional.ofNullable(username)
                .orElseGet(() -> getDefaultName());
        System.out.println(result2);

//        String username = null;
//        String defaultName = getDefaultName();
//        String result1 = Optional.ofNullable(username)
//                .orElse(defaultName);
//        System.out.println(result1);
//
//        Supplier<String> supplier = () -> getDefaultName();
//        String result2 = Optional.ofNullable(username)
//                .orElseGet(supplier);
//        System.out.println(result2);
    }

    private static String getDefaultName() {
        System.out.println("############## getDefaultName");
        return "no name";
    }

}