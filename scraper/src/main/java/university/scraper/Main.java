package university.scraper;

import university.domain.MuralMessage;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        UniversityClient universityClient = new UniversityClient(
                new AuthWorker(),
                new DashboardWorker(),
                null
        );

        UserSession user = universityClient.login(args[0], args[1]);
    
        List<MuralMessage> muralMessages = universityClient.getMuralMessages(user);
    
        muralMessages.stream().map(MuralMessage::getDescription).forEach(System.out::println);
        
        System.out.println(user.getCursoNome());
        

    }

}
