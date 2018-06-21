
package carwashsystem.carWashExeception;

/**
 *
 * @author 
 */
public class DataRepetitionException extends Exception {

    public DataRepetitionException() {
    }

    /**
    
     * @param msg the detail message.
     */
    public DataRepetitionException(String msg) {
        super(msg);
    }
}
