package com.capgemini.loanpecunia;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.loan.dao.LoanDisbursalDao;
import com.loan.dao.LoanRequestDao;
import com.loan.entities.LoanDisbursal;
import com.loan.entities.LoanRequests;
import com.loan.service.LoanPecuniaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanPecuniaApplicationTests {

	@Autowired 
	LoanPecuniaService service;
	
	@MockBean
	LoanRequestDao rdao;
	
	@MockBean
	LoanDisbursalDao ddao;
	
	

	
	@Test
	public void loanRequestTestMock() {
		LoanRequests loanreq = new LoanRequests(5,"22222222224",6000,29,570,5,"pending","car loan");
		when(rdao.save(loanreq)).thenReturn(loanreq);
		assertEquals(loanreq, rdao.save(loanreq));
	}
	
	
	@Test
	public void loandisbursalTest() {

	
		Mockito.when(ddao.findAll()).thenReturn(Stream.of(new LoanDisbursal()).collect(Collectors.toList()));
		assertEquals(1, ddao.findAll().size());

	}
	
	
}
