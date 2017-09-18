package com.zyu.wsecx.crypto;

/**
 * the foundation class for the exceptions thrown by the crypto packages.
 */
public class RuntimeCryptoException extends RuntimeException {
	/**
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	  */
	
	
	private static final long serialVersionUID = -9060220634435943880L;

	/**
	 * base constructor.
	 */
	public RuntimeCryptoException() {
	}

	/**
	 * create a RuntimeCryptoException with the given message.
	 * 
	 * @param message
	 *            the message to be carried with the exception.
	 */
	public RuntimeCryptoException(String message) {
		super(message);
	}
}
