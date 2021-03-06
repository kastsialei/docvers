package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class IntegrityConstraintException extends DAOException {
    private Exception e;
    private String message;

    public IntegrityConstraintException(Exception e, String message) {
        super(e);

        this.e = e;
        this.message = message;
    }

    public String toString() {

        return "Integrity Constraint Exception: " + e.getMessage() + " with message " + message;

    }
}
