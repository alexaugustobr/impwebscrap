import java.util.HashMap;
import java.util.Map;

public class ImpAuthenticatedUser {

    private final String ra;
    private final String password;
    private Map<String, String> authCookies;
    private String name;
    private String courseName;

    public ImpAuthenticatedUser(String ra, String password, Map<String, String> authCookies, String name, String courseName) {
        this.ra = ra;
        this.password = password;
        this.authCookies = authCookies;
        this.name = name;
        this.courseName = courseName;
    }

    Map<String, String> getAuthCookies() {
        return new HashMap<String, String>(authCookies);
    }

    public String getRa() {
        return ra;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCourseName() {
        return courseName;
    }
}
