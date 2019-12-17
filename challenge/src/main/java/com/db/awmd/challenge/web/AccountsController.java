package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.MoneyTransfer;
import com.db.awmd.challenge.exception.BankTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.NotificationService;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import javax.xml.ws.Response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;
  //private final NotificationService notification;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  

  
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
    log.info("Creating account {}", account);

    try {
    this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }
  
 /* @RequestMapping(value= "/transferBetweenAcc", method= RequestMethod.GET)
  public Response betweenAccounts(Model model) {
	  model.addAttribute("accountFrom","");
	  model.addAttribute("accountTo","");
	  model.addAttribute("amount to transfer","");
	  
	  List<Account> result = accountsService.transferBetween(String , Account , BigDecimal );
      return Response.ok().entity(result).build();
	  
  }
*/
  
  @RequestMapping(value = "/transferAmount", method = RequestMethod.POST)
  public void betweenAccounts(Model model, MoneyTransfer sendMoneyFrom) throws BankTransactionException {

      System.out.println("Send Money: " + sendMoneyFrom.getAmount());

      accountsService.transferBetween(sendMoneyFrom.getFrom(), 
    		  sendMoneyFrom.getTo(), //
    		  sendMoneyFrom.getAmount());
      //return "Amount Transferred";
      
  }
  
}
