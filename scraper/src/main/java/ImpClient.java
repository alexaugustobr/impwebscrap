public class ImpClient {

    private final ImpLoginWorker loginWorker = new ImpLoginWorker();

    public ImpAuthenticatedUser login(ImpLoginRequest loginRequest) throws ImpLoginException {
        return this.loginWorker.login(loginRequest);
    }

    public String notasFaltas(ImpAuthenticatedUser user)  {

        return "";
    }

    public String requerimentos() {



        return "";
    }


}
