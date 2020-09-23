package com.loan.service;

import java.util.ArrayList;
import java.util.List;

import com.loan.entities.LoanDisbursal;
import com.loan.entities.LoanRequests;

public interface LoanPecuniaService {

	public String loanRequest(LoanRequests loanreq);
	
	public ArrayList<LoanRequests> getAllRequests();
	
	public  List<LoanDisbursal> getApproveLoans();
	
	public List<LoanDisbursal> getRejectedLoans();
	
	
}
