package com.loan.exceptions;

@SuppressWarnings("serial")
public class BankAccountNotFound extends RuntimeException {
	public BankAccountNotFound(String exception) {
        super(exception);

}
}
