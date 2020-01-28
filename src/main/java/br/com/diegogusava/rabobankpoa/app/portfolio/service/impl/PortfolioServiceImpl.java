package br.com.diegogusava.rabobankpoa.app.portfolio.service.impl;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.account.service.AccountService;
import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import br.com.diegogusava.rabobankpoa.app.card.service.CreditCardService;
import br.com.diegogusava.rabobankpoa.app.card.service.DebitCardService;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import br.com.diegogusava.rabobankpoa.app.operator.service.AccountOperatorService;
import br.com.diegogusava.rabobankpoa.app.portfolio.domain.AccountDetail;
import br.com.diegogusava.rabobankpoa.app.portfolio.exception.AccountAccessDeniedException;
import br.com.diegogusava.rabobankpoa.app.portfolio.exception.AccountNotFoundException;
import br.com.diegogusava.rabobankpoa.app.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class PortfolioServiceImpl implements PortfolioService {

    private final AccountService accountService;
    private final AccountOperatorService accountOperatorService;
    private final DebitCardService debitCardService;
    private final CreditCardService creditCardService;

    @Override
    public List<Account> findAccountsByOperatorId(final String operatorId) {
        final Set<String> accountIds = accountOperatorService.findActiveAccountIdsByOperatorId(operatorId);
        return accountService.findAllActiveAccountByIds(accountIds);
    }

    @Override
    public AccountDetail findByAccountIdAndOperatorId(final String accountId, final String operatorId) {
        final boolean viewAllowed = accountOperatorService.isAllowedByAccountIdAndOperatorId(AccountPermission.VIEW,
                accountId, operatorId);

        if (!viewAllowed) {
            throw new AccountAccessDeniedException(accountId, operatorId);
        }

        final Account account = accountService.findActiveAccountById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        final List<CreditCard> creditCards = creditCardService.findActiveCreditCardsByAccountId(account.getId());
        final List<DebitCard> debitCards = debitCardService.findActiveDebitCardsByAccountId(account.getId());

        final AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(account.getId());
        accountDetail.setName(account.getName());
        accountDetail.setAccountHolder(account.getAccountHolder());
        accountDetail.setBalance(account.getBalance());
        accountDetail.setCreatedDate(account.getCreated());
        accountDetail.setCreditCards(creditCards);
        accountDetail.setDebitCards(debitCards);
        return accountDetail;
    }

}
