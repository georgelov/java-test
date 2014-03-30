/**
 * 
 */
package se.tain.exercise.refactor.service;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public class ServiceUnavailale extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -796450300814682682L;

    /**
     * 
     */
    public ServiceUnavailale() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ServiceUnavailale(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceUnavailale(final String message, final Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public ServiceUnavailale(final String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ServiceUnavailale(final Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
