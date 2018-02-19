package by.epam.exeption.dao;

/**
 * An exception that provides information about violation in the work of the
 * connection pool {@link by.epam.dao.impl.pool.ConnectionPool}
 */

public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionPoolException(String message){
		super(message);
	}
	
	public ConnectionPoolException(String message, Exception e){
		super(message, e);
	}	

}