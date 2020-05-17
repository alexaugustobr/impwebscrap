package university.scraper;

import lombok.AllArgsConstructor;
import university.domain.ReportCard;
import university.domain.MuralMessage;
import university.domain.SchoolEnrolment;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UniversityClient {

    private final AuthWorker auth;
    private final DashboardWorker dashboard;
    private final SchoolEnrolmentWorker schoolEnrolment;
    
    public UserSession login(String ra, String password) throws LoginException {
        return this.auth.login(new LoginRequest(ra, password));
    }

    public List<SchoolEnrolment> getSchoolEnrolment(UserSession userSession)  {
        return schoolEnrolment.extractAllSchoolEnrollments(userSession);
    }
    
    public ReportCard getReportCard(UserSession userSession)  {
        return new ReportCard(new ArrayList<>());
    }
    
    public List<MuralMessage> getMuralMessages(UserSession userSession) {
        return dashboard.messages(userSession);
    }
}
