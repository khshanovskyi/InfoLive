package ua.nure.khshanovskyi.infoLife.exception;

/**
 * New exception entity for work exceptions in DAO layer.
 *
 * @author Khshanovskyi Pavlo
 */
public class DaoException extends RuntimeException{

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
