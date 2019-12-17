package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.BankTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }

  @Override
  public Account getAccount(String accountId) {
    return accounts.get(accountId);
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }


  @Override
  public void addAmount(String accountId, BigDecimal amount) throws BankTransactionException {
	// TODO Auto-generated method stub
	Account account= this.getAccount(accountId);
	if(account == null)
		throw new BankTransactionException("Account not found " + accountId);
	
	BigDecimal newBalance = amount.add(account.getBalance());
	if(amount.compareTo(newBalance)<= 0)
	{
		throw new BankTransactionException("Insuffcient balance");
	}
	account.setBalance(newBalance);
  }

  @Override
  public void transferBetween(String accountFrom, String accountTo, BigDecimal amount) throws BankTransactionException {
	// TODO Auto-generated method stub
	addAmount(accountFrom, amount);
	addAmount(accountTo, amount.negate());
  }


}
