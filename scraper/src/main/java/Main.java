public class Main {

    public static void main(String[] args) throws Exception {

        ImpClient impClient = new ImpClient();

        ImpAuthenticatedUser user = impClient.login(new ImpLoginRequest(args[0], args[1]));

        impClient.notasFaltas(user);



    }

}
