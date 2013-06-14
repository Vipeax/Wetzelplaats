/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.exception;

/**
 * Excpetion that needs to be thrown whenever a duplicate entity has been tried
 * to put in database
 *
 * @author Timo
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
