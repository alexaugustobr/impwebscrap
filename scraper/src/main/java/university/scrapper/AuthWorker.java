package university.scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class AuthWorker {

    private static final String LOGIN_SUCCESSFUL_JSON = "\"success\":true";
    private static final String LOGIN_FAIL_JSON = "\"success\":false";

    AuthenticatedUser login(LoginRequest loginRequest) throws LoginException {
        try {
            Connection.Response response = sendLoginRequest(loginRequest);

            if (!isLoginSuccessful(response)) {
                throw new LoginException("Login failed, wrong username or password.");
            }

            Map<String,String> authCookies = response.cookies();

            StudentInfo studentInfo = getStudentInfo(authCookies);

            return new AuthenticatedUser(loginRequest.getRa(), loginRequest.getPassword(), authCookies, studentInfo.getName(), studentInfo.getCourseName());
        } catch (IOException e) {
            throw new LoginException(e);
        }
    }

    private StudentInfo getStudentInfo(Map<String, String> authCookies) throws IOException, LoginException {
        Document parse = Jsoup.connect(UniversityUrl.STUDENT_AREA)
                .method(Connection.Method.GET)
                .cookies(authCookies)
                .execute().parse();

        return extractStudentInfo(parse);
    }

    private StudentInfo extractStudentInfo(Document document) throws LoginException {
        try {
            String ra;
            String name;
            String courseName;
            
            Element loginContent = document.getElementById("LoginContent");
    
            Elements divs = loginContent.selectFirst(".span4")
                    .getElementsByTag("div");
    
            name = divs.get(1).text().replace("Olá, ", "");
    
            ra = divs.get(2).text().replace("RA: ", "");
    
            divs = loginContent.selectFirst(".span8")
                    .getElementsByTag("div");
    
            courseName = divs.get(0).text().replace("Curso: ", "");
    
            return new StudentInfo(ra, name, courseName);
        } catch (Exception e){
            throw new LoginException("Can't extract student info", e);
        }
    }

    private Connection.Response sendLoginRequest(LoginRequest loginRequest) throws IOException {
        Connection connection = Jsoup.connect(UniversityUrl.LOGIN).method(Connection.Method.GET);
        Map<String, String> cookies = connection.execute().cookies();
        connection.method(Connection.Method.POST);
        connection.cookies(cookies);
        connection.data(loginRequest.asLoginForm());
        return connection.execute();
    }

    private boolean isLoginSuccessful(Connection.Response response) throws LoginException {
        String body = response.body();
        if (!body.contains(LOGIN_SUCCESSFUL_JSON) && !body.contains(LOGIN_FAIL_JSON)) {
            throw new LoginException("Login response doesn't contains fail or success message. Body:\n\n" + body);
        }
        return body.contains(LOGIN_SUCCESSFUL_JSON);
    }
}