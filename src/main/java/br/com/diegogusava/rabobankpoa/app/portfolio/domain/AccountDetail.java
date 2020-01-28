package br.com.diegogusava.rabobankpoa.app.portfolio.domain;

import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AccountDetail {

    private String id;
    private String name;
    private String accountHolder;
    private LocalDate createdDate;
    private BigDecimal balance;
    private List<DebitCard> debitCards;
    private List<CreditCard> creditCards;

}
