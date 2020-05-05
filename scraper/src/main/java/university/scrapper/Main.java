package university.scrapper;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        UniversityClient universityClient = new UniversityClient();

        AuthenticatedUser user = universityClient.login(new LoginRequest(args[0], args[1]));
    
        List<Recado> recados = universityClient.recados(user);
    
        System.out.println(user.getCourseName());
        

    }

}
