package com.loan.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.dao.AccountDao;
import com.loan.dao.LoanDisbursalDao;
import com.loan.dao.LoanRequestDao;
import com.loan.entities.Account;
import com.loan.entities.LoanDisbursal;
import com.loan.entities.LoanRequests;
import com.loan.exceptions.BankAccountNotFound;

@Service
public class LoanPecuniaServiceImpl implements LoanPecuniaService {
    
	@Autowired
	private LoanRequestDao dao;
	@Autowired
	private AccountDao account;
	@Autowired
	private LoanDisbursalDao disburseDao;
	
	LoanDisbursal disburse = new LoanDisbursal();
	
	
	
	@Override
	public String loanRequest(LoanRequests loanreq) {
		
	      String s = loanreq.getAccountId();
	    
	      Optional<Account> details = account.findById(s);  //Check and fetch the accountId details
	      
	     
	      if(details.isPresent()) {  
	    	  dao.save(loanreq); 				 //save the loan request details 
	       
	    	  return "Your Loan Request is successful";
	      }
	      else
	      {
	    	  throw new BankAccountNotFound("No BankAccount found with " + loanreq.getAccountId() + "\n You need to have  Bank Account to apply Loan");
	      }
		
	}

	@Override
	public ArrayList<LoanRequests> getAllRequests() {
		
		return (ArrayList<LoanRequests>) dao.findAll();  //fetching all LoanRequests
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<LoanDisbursal> getApproveLoans() {
		ArrayList<LoanRequests> approve = disburseDao.getApprovedLoans();   //fetching approved loans
		Iterator iter = approve.iterator();
		while (iter.hasNext()) {
			LoanRequests l = (LoanRequests) iter.next();   
			if(!(disburseDao.existsById(l.getLoanId()))) {
				//setting the approved loanrequest details to loandisbursal
			disburse.setAccountId(l.getAccountId());
			disburse.setCreditScore(l.getCreditScore());
			disburse.setLoanAmount(l.getLoanAmount());
			disburse.setLoanId(l.getLoanId());
			disburse.setLoanRoi(l.getLoanRoi());
			disburse.setLoanStatus("Accepted");
			disburse.setLoanTenure(l.getLoanTenure());
			disburse.setLoanType(l.getLoanType());
			//calculating interest
			double interest = (l.getLoanAmount() * l.getLoanTenure() * l.getLoanRoi() / (100 * 12));
			//calculating EMI
			double emi = ((l.getLoanAmount() + interest) / l.getLoanTenure());
			emi=Math.round(emi*100)/100;    //Getting the closest long emi value
			disburse.setEmi(emi);			//setting emi value to loandisbursal emi 
			System.out.println(disburse);
			disburseDao.save(disburse);		//saving the values that are being set till now to DisbursalLoan table in database
		}else {
			continue;
		}
			}
		return disburseDao.findAllAccepted();  //returning all accepted loan approvals
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<LoanDisbursal> getRejectedLoans() {
		ArrayList<LoanRequests> approve = disburseDao.getRejectedLoans();  //fetch all rejected loanrequest details
		Iterator iter = approve.iterator();
		while (iter.hasNext()) {
			LoanRequests l = (LoanRequests) iter.next();
			//setting all rejected loan details to loandisbursal values
			disburse.setAccountId(l.getAccountId());
			disburse.setCreditScore(l.getCreditScore());
			disburse.setLoanAmount(0);
			disburse.setLoanId(l.getLoanId());
			disburse.setLoanRoi(l.getLoanRoi());
			disburse.setLoanStatus("Rejected");
			disburse.setLoanTenure(l.getLoanTenure());
			disburse.setLoanType(l.getLoanType());
			disburse.setEmi(0);
			System.out.println(disburse);
			disburseDao.save(disburse);		   //saving the values that are being set till now to DisbursalLoan table in database	
		}
		return disburseDao.findAllRejected();  //returning all rejected loan requests
	}

	
	
	
}
