package io._29cu.usmserver.common.exceptions;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -905031374766346394L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
