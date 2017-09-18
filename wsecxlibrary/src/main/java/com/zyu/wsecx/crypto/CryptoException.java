package com.zyu.wsecx.crypto;

/**
 * the foundation class for the hard exceptions thrown by the crypto packages.
 */
public class CryptoException extends Exception {
	/**
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	  */
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * base constructor.
	 */
	public CryptoException() {
	}

	/**
	 * create a CryptoException with the given message.
	 * 
	 * @param message
	 *            the message to be carried with the exception.
	 */
	public CryptoException(String message) {
		super(message);
	}
}
