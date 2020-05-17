package university.scraper;

public class LoginException extends Exception {
    public LoginException(Exception e) {
        super(e);
    }

    public LoginException() {
    }

    public LoginException(String s) {
        super(s);
    }
    
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
