package br.com.diegogusava.rabobankpoa.app.account.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Account {

    private String id;
    private String name;
    private String accountHolder;
    private LocalDate created;
    private LocalDate ended;
    private BigDecimal balance;

}