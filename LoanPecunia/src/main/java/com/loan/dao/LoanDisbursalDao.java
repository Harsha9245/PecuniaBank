package com.loan.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loan.entities.LoanDisbursal;
import com.loan.entities.LoanRequests;

@Repository
public interface LoanDisbursalDao extends JpaRepository<LoanDisbursal, Integer> 
{
	//Approving Loans if creditscore>670
	@Query("select r from LoanRequests r where creditScore>670")
	ArrayList<LoanRequests> getApprovedLoans();
	
	//Approving Loans if creditscore>670
	@Query("select r from LoanRequests r where creditScore<=670")
	ArrayList<LoanRequests> getRejectedLoans();
	
	//If CreditScore>670 then Loan is accepted
	@Query("select r from LoanDisbursal r where loanStatus='Accepted'")
	List<LoanDisbursal> findAllAccepted();
	
	//If CreditScore<670 then Loan is rejected
	@Query("select r from LoanDisbursal r where loanStatus='Rejected'")
	List<LoanDisbursal> findAllRejected();
	
	
	
}


