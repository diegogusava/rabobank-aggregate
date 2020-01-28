package br.com.diegogusava.rabobankpoa.app.portfolio.exception;

public class AccountAccessDeniedException extends RuntimeException {
    public AccountAccessDeniedException(final String accountId, final String operatorId) {
    }
}
