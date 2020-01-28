package br.com.diegogusava.rabobankpoa.app.portfolio.controller;

import br.com.diegogusava.rabobankpoa.app.portfolio.domain.AccountDetail;
import br.com.diegogusava.rabobankpoa.app.portfolio.service.PortfolioService;
import br.com.diegogusava.rabobankpoa.generated.api.PortfolioApi;
import br.com.diegogusava.rabobankpoa.generated.model.*;
import br.com.diegogusava.rabobankpoa.security.JwtTokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasAuthority('OPERATOR')")
@RequiredArgsConstructor
@RestController
public class PortfolioController implements PortfolioApi {

    private final PortfolioService portfolioService;

    @Override
    public ResponseEntity<List<AccountDto>> findAccounts() {
        final String userId = JwtTokenExtractor.getOperatorId();
        final List<AccountDto> dtos = portfolioService.findAccountsByOperatorId(userId)
                .stream()
                .map(a -> {
                    var dto = new AccountDto();
                    dto.setId(a.getId());
                    dto.setName(a.getName());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<AccountDetailDto> findAccountById(String accountId) {
        final String userId = JwtTokenExtractor.getOperatorId();
        final AccountDetail detail = portfolioService.findByAccountIdAndOperatorId(accountId, userId);
        return ResponseEntity.ok(mapDetailToDto(detail));
    }

    private AccountDetailDto mapDetailToDto(final AccountDetail detail) {
        final AccountDetailDto dto = new AccountDetailDto();
        dto.setId(detail.getId());
        dto.setName(detail.getName());
        dto.setAccountHolder(detail.getAccountHolder());
        dto.setBalance(detail.getBalance());
        dto.setCreatedDate(detail.getCreatedDate());
        dto.setCreditCards(detail.getCreditCards().stream().map(cc -> {
            final var ccdto = new CreditCardDto();
            ccdto.setId(cc.getId());
            ccdto.setCardHolder(cc.getCardHolder());
            ccdto.setCardNumber(cc.getCardNumber());
            ccdto.setSequenceNumber(cc.getSequenceNumber());
            ccdto.setStatus(CardStatusDto.valueOf(cc.getStatus().name()));
            return ccdto;
        }).collect(Collectors.toList()));
        dto.setDebitCards(detail.getDebitCards().stream().map(dc -> {
            final var dbdto = new DebitCardDto();
            dbdto.setId(dc.getId());
            dbdto.setCardHolder(dc.getCardHolder());
            dbdto.setCardNumber(dc.getCardNumber());
            dbdto.setSequenceNumber(dc.getSequenceNumber());
            dbdto.setStatus(CardStatusDto.valueOf(dc.getStatus().name()));
            final CardLimitDto atmLimit = new CardLimitDto();
            atmLimit.setAmount(dc.getAtmLimit().getAmount());
            atmLimit.setPeriod(PeriodUnitDto.valueOf(dc.getAtmLimit().getPeriod().name()));
            dbdto.setAtmLimit(atmLimit);
            final CardLimitDto posLimit = new CardLimitDto();
            posLimit.setAmount(dc.getPosLimit().getAmount());
            posLimit.setPeriod(PeriodUnitDto.valueOf(dc.getPosLimit().getPeriod().name()));
            dbdto.setPosLimit(posLimit);
            dbdto.setContactless(dc.isContactless());
            return dbdto;
        }).collect(Collectors.toList()));
        return dto;
    }

}

