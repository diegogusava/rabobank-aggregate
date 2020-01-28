package br.com.diegogusava.rabobankpoa.app.card.service.impl;

import br.com.diegogusava.rabobankpoa.app.card.repository.DebitCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceImplTest {

    @Test
    void findActiveDebitCardsByAccountId(@Mock DebitCardRepository repository) {
        var service = new DebitCardServiceImpl(repository);

        service.findActiveDebitCardsByAccountId("1");

        verify(repository).findActiveDebitCardByAccountId("1");
    }

}