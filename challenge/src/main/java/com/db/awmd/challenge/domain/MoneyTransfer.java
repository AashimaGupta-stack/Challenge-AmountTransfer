package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
@Data
public class MoneyTransfer {
	
	 @NotNull
	 @NotEmpty
	 private final String from;
	 @NotNull
	 @NotEmpty
	 private final String to;
	 
	 @NotNull
	 @Min(value = 0, message = "Initial balance must be positive.")
	 private final BigDecimal amount;
	 
	 
}
