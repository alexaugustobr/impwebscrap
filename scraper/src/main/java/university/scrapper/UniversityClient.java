package university.scrapper;

import java.util.List;

public class UniversityClient {

    private final AuthWorker auth = new AuthWorker();
    private final DashboardWorker dashboard = new DashboardWorker();

    public AuthenticatedUser login(LoginRequest loginRequest) throws LoginException {
        return this.auth.login(loginRequest);
    }

    public String notasFaltas(AuthenticatedUser user)  {
        return "";
    }

    public String requerimentos() {
        return "";
    }
    
    public List<Recado> recados(AuthenticatedUser user) {
        return dashboard.recados(user);
    }
}
