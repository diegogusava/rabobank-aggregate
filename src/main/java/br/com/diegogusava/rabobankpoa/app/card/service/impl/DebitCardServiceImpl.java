package br.com.diegogusava.rabobankpoa.app.card.service.impl;

import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import br.com.diegogusava.rabobankpoa.app.card.repository.DebitCardRepository;
import br.com.diegogusava.rabobankpoa.app.card.service.DebitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DebitCardServiceImpl implements DebitCardService {

    private final DebitCardRepository repository;

    @Override
    public List<DebitCard> findActiveDebitCardsByAccountId(String accountId) {
        return repository.findActiveDebitCardByAccountId(accountId);
    }
}
