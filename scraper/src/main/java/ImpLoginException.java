public class ImpLoginException extends Exception {
    public ImpLoginException(Exception e) {
        super(e);
    }

    public ImpLoginException() {
    }

    public ImpLoginException(String s) {
        super(s);
    }
}
