package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

/**
 * @author Tushar Chokshi @ 11/7/16.
 */
// pg 201
// The Toon class using Result.Empty for optional data
public class Toon {
    private final String firstName;
    private final String lastName;
    private final Result<String> email;

    Toon(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = Result.empty();
    }

    Toon(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = Result.success(email);
    }

    public Result<String> getEmail() {
        return email;
    }

    public static void main(String[] args) {
        MyMap<String, Toon> toons = new MyMap<String, Toon>()
                .put("Mickey", new Toon("Mickey", "Mouse", "mickey@disney.com"))
                .put("Minnie", new Toon("Minnie", "Mouse"))
                .put("Donald", new Toon("Donald", "Duck", "donald@disney.com"));
        Result<String> result =
                getName().flatMap(toons::get).flatMap(Toon::getEmail);
        System.out.println(result);
    }

    public static Result<String> getName() {
        return Result.success("Mickey");
        //return Result.failure(new IOException("Input error"));
        //return Result.success("Minnie");
        //return Result.success("Goofy");
    }

}