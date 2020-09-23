package com.loan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loan.entities.LoanDisbursal;
import com.loan.entities.LoanRequests;
import com.loan.exceptions.NullFoundException;
import com.loan.service.LoanPecuniaService;

@RestController
@RequestMapping("/loan")

public class LoanController {

	@Autowired
	LoanPecuniaService service;
	
	@PostMapping("/request")
	public ResponseEntity<String> loanRequest(@RequestBody LoanRequests loanreq) //Used to take input values
	{
		if(loanreq.getAccountId()==null) //Checking whether the accountID is available
		{
			throw new NullFoundException("cannot be null");
		}
		else
		{
			String request = service.loanRequest(loanreq);   //Invoking ServiceLayer LoanRequest Method
			return new ResponseEntity<String>(request, new HttpHeaders(), HttpStatus.OK); 
		}
	}
	
	@GetMapping("/getAllRequests")
	public ResponseEntity<List<LoanRequests>> getAllRequests(){
		ArrayList<LoanRequests> requests = service.getAllRequests();   //Invoking ServiceLayer Method to fetch all loan requests
		return new ResponseEntity<List<LoanRequests>>(requests, HttpStatus.OK);
	}
	
   @GetMapping("/approvedrequests")
	public ArrayList<LoanDisbursal> getApproveLoans(){
	   
			return (ArrayList<LoanDisbursal>)service.getApproveLoans(); //Invoking Method to fetch approved loanrequests
		
	}
	
	
	@GetMapping("/rejectedrequests")
	public ArrayList<LoanDisbursal> getRejectedLoans(){
		
			return (ArrayList<LoanDisbursal>)service.getRejectedLoans(); //Invoking method to get rejected loanrequests
		
	}
	
}
