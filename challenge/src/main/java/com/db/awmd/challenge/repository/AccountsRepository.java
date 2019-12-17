package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.List;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.BankTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;


public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();
  
  void addAmount(String accountId, BigDecimal amount) throws BankTransactionException;
  
  void transferBetween(String accountFrom, String accountTo,BigDecimal amount) throws BankTransactionException;

  
}
