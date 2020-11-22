package vaccine.exceptions;

public class HigherNeedThanProductionException extends Exception{
    public HigherNeedThanProductionException(String message) {
        super(message);
    }
}
