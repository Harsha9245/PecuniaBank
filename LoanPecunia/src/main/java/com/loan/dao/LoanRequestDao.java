package com.loan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loan.entities.LoanRequests;

@Repository
public interface LoanRequestDao extends JpaRepository<LoanRequests, Integer>{

	//Fetching LoanRequests by AccountNumber
	@Query("select req from LoanRequests req where accountId=?1")
	List<LoanRequests> selectById(@Param("c") String s1);
	

}
