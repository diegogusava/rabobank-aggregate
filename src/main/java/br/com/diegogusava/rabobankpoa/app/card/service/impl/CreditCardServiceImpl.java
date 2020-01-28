package br.com.diegogusava.rabobankpoa.app.card.service.impl;

import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import br.com.diegogusava.rabobankpoa.app.card.repository.CreditCardRepository;
import br.com.diegogusava.rabobankpoa.app.card.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository repository;

    @Override
    public List<CreditCard> findActiveCreditCardsByAccountId(String accountId) {
        return repository.findActiveCreditCardByAccountId(accountId);
    }

}
