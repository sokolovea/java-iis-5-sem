package ru.rsreu.kuznecovsokolov12.datalayer;

public class DBTypeException extends RuntimeException {
	/**
	 * Added to prevent CheckStyle warning
	 */
	private static final long serialVersionUID = 1L;

	public DBTypeException() {
		super();
	}

	public DBTypeException(String message) {
		super(message);
	}
}
