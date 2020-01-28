package br.com.diegogusava.rabobankpoa.app.card.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(of = "id")
@Data
public class CreditCard {

    private String id;
    private String accountId;
    private CardStatus status;
    private String cardNumber;
    private String sequenceNumber;
    private String cardHolder;
    private BigDecimal monthlyLimit;

}
