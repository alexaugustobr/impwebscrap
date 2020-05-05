package university.scrapper;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatedUser {

    private final String ra;
    private final String password;
    private final Map<String, String> authCookies;
    private final String name;
    private final String courseName;

    public AuthenticatedUser(String ra, String password, Map<String, String> authCookies, String name, String courseName) {
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
