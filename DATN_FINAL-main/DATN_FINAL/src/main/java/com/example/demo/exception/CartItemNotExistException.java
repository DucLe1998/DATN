package com.example.demo.exception;

public class CartItemNotExistException extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartItemNotExistException(String msg) {
        super(msg);
    }
}
