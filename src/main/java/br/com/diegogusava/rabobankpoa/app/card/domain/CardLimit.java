package br.com.diegogusava.rabobankpoa.app.card.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardLimit {

    private BigDecimal amount;
    private PeriodUnit period;

}
