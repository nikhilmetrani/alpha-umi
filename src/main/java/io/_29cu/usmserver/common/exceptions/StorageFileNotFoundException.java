package io._29cu.usmserver.common.exceptions;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = 7243171547195664707L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}