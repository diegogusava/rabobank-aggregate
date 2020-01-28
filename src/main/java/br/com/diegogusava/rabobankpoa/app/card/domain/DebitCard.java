package br.com.diegogusava.rabobankpoa.app.card.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@Data
public class DebitCard {

    private String id;
    private String accountId;
    private CardStatus status;
    private String cardNumber;
    private String sequenceNumber;
    private String cardHolder;
    private CardLimit atmLimit;
    private CardLimit posLimit;
    private boolean contactless;

}
