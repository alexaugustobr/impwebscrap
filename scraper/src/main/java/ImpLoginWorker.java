import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class ImpLoginWorker extends ImpWorker {

    private final String loginUrl = "https://account.impacta.edu.br/account/enter.php";
    private final String studentAreaUrl = "https://account.impacta.edu.br/aluno/default.php";

    private static final String loginSuccessfulJSON = "\"success\":true";
    private static final String loginFailJSON = "\"success\":false";

    ImpAuthenticatedUser login(ImpLoginRequest loginRequest) throws ImpLoginException {
        try {
            Connection.Response response = sendLoginRequest(loginRequest);

            if (!isLoginSuccessful(response)) {
                throw new ImpLoginException("Login failed, wrong username or password.");
            }

            Map<String,String> authCookies = response.cookies();

            ImpStudentInfo studentInfo = getStudentInfo(authCookies);

            return new ImpAuthenticatedUser(loginRequest.getRa(), loginRequest.getPassword(), authCookies, studentInfo.getName(), studentInfo.getCourseName());
        } catch (IOException e) {
            throw new ImpLoginException(e);
        }
    }

    private ImpStudentInfo getStudentInfo(Map<String, String> authCookies) throws IOException, ImpLoginException {
        Document parse = Jsoup.connect(studentAreaUrl)
                .method(Connection.Method.GET)
                .cookies(authCookies)
                .execute().parse();

        return extractStudentInfo(parse);
    }

    private ImpStudentInfo extractStudentInfo(Document document) throws ImpLoginException {

        String ra;
        String name;
        String courseName;

        Element loginContent = document.getElementById("LoginContent");

        Elements divs = loginContent.selectFirst(".span4")
                .getElementsByTag("div");

        if (divs == null || divs.isEmpty() || divs.size() < 2) {
            throw new ImpLoginException("Can't extract student info");
        }

        name = divs.get(1).text().replace("Olá, ", "");

        ra = divs.get(2).text().replace("RA: ", "");

        divs = loginContent.selectFirst(".span8")
                .getElementsByTag("div");

        if (divs == null || divs.isEmpty()) {
            throw new ImpLoginException("Can't extract student info");
        }

        courseName = divs.get(0).text().replace("Curso: ", "");

        return new ImpStudentInfo(ra, name, courseName);
    }

    private Connection.Response sendLoginRequest(ImpLoginRequest loginRequest) throws IOException {
        Connection connection = Jsoup.connect(baseUrl).method(Connection.Method.GET);
        Map<String, String> cookies = connection.execute().cookies();

        connection.url(loginUrl);
        connection.method(Connection.Method.POST);
        connection.cookies(cookies);
        connection.data(loginRequest.asLoginForm());
        return connection.execute();
    }

    private boolean isLoginSuccessful(Connection.Response response) throws ImpLoginException {
        String body = response.body();
        if (!body.contains(loginSuccessfulJSON) && !body.contains(loginFailJSON)) {
            throw new ImpLoginException("Login response doesn't contains fail or success message. Body:\n\n" + body);
        }
        return body.contains(loginSuccessfulJSON);
    }
}