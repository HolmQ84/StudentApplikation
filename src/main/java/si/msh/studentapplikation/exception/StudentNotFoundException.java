package si.msh.studentapplikation.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String exception) {
        super("HER ER EXCEPTION BESKEDEN: "+exception);
    }
}
