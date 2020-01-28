package br.com.diegogusava.rabobankpoa.config;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.auth.domain.User;
import br.com.diegogusava.rabobankpoa.app.card.domain.*;
import br.com.diegogusava.rabobankpoa.app.operator.domain.*;
import br.com.diegogusava.rabobankpoa.app.portfolio.controller.PortfolioRoles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission.*;

public class MockData {

    public static List<User> getUsers() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return List.of(
                new User("1", "1", "super", passwordEncoder.encode("super"),
                        List.of(PortfolioRoles.ROLE_OPERATOR)),
                new User("2", "2", "frodo", passwordEncoder.encode("frodo"),
                        List.of(PortfolioRoles.ROLE_OPERATOR)),
                new User("3", "3", "aragorn", passwordEncoder.encode("aragorn"),
                        List.of(PortfolioRoles.ROLE_OPERATOR)),
                new User("4", "4", "boromir", passwordEncoder.encode("boromir"),
                        List.of(PortfolioRoles.ROLE_OPERATOR))
        );
    }

    public static List<Account> getAccounts() {
        final Account account123123123 = new Account();
        account123123123.setId("123123123");
        account123123123.setName("Personal Account");
        account123123123.setAccountHolder("Super duper employee");
        account123123123.setCreated(LocalDate.of(2007, 10, 12));
        account123123123.setBalance(BigDecimal.valueOf(-125));

        final Account account123456789 = new Account();
        account123456789.setId("123456789");
        account123456789.setName("Company Account");
        account123456789.setAccountHolder("Super duper company");
        account123456789.setCreated(LocalDate.of(2007, 10, 12));
        account123456789.setBalance(BigDecimal.valueOf(750));

        final Account account343434343 = new Account();
        account343434343.setId("343434343");
        account343434343.setName("Company Account");
        account343434343.setAccountHolder("Super duper company");
        account343434343.setCreated(LocalDate.of(2007, 10, 12));
        account343434343.setBalance(BigDecimal.valueOf(6000));

        final Account account987654321 = new Account();
        account987654321.setId("987654321");
        account987654321.setName("Company Account");
        account987654321.setAccountHolder("Super duper company");
        account987654321.setCreated(LocalDate.of(2007, 10, 12));
        account987654321.setEnded(LocalDate.of(2019, 9, 1));
        account987654321.setBalance(BigDecimal.ZERO);

        final Account account101010101 = new Account();
        account101010101.setId("101010101");
        account101010101.setName("Personal Account");
        account101010101.setCreated(LocalDate.of(2007, 10, 12));
        account101010101.setEnded(LocalDate.of(2019, 9, 1));
        account101010101.setBalance(BigDecimal.valueOf(1000));

        return List.of(account123123123, account123456789, account343434343, account987654321, account101010101);
    }

    public static List<Operator> getOperators() {
        Operator op1 = new Operator("1", "Super duper employee", Set.of("2"));
        Operator op2 = new Operator("2", "Frodo Basggins", Set.of("1", "2"));
        Operator op3 = new Operator("3", "Aragorn", Set.of("1", "2"));
        Operator op4 = new Operator("4", "Boromir", Set.of("1", "2"));
        Operator op5 = new Operator("5", "Hallo", Set.of());
        return List.of(op1, op2, op3, op4, op5);
    }

    public static List<OperatorGroup> getOperatorGroup() {
        OperatorGroup og1 = new OperatorGroup();
        og1.setId("1");
        og1.setName("Fellowship of the ring");
        og1.setOperatorIds(List.of("2", "3", "4"));

        OperatorGroup og2 = new OperatorGroup();
        og2.setId("2");
        og2.setName("Super duper company");
        og2.setOperatorIds(List.of("1", "2", "3", "4"));

        return List.of(og1, og2);
    }

    public static List<AccountOperator> getAccountOperators() {
        List<AccountOperator> result = new ArrayList<>();
        int id = 0;
        AccountOperator ao0001 = new AccountOperator();
        ao0001.setId(String.valueOf(++id));
        ao0001.setOperatorGroupId("1");
        ao0001.setAccountId("123456789");
        ao0001.setDirection(PermissionDirection.GIVEN);
        ao0001.setPermissions(Set.of(DEBIT_CARD, VIEW, PAYMENT));
        ao0001.setStatus(AccountOperatorStatus.ACTIVE);
        result.add(ao0001);

        AccountOperator ao0002 = new AccountOperator();
        ao0002.setId(String.valueOf(++id));
        ao0002.setOperatorId("1");
        ao0002.setAccountId("987654321");
        ao0002.setDirection(PermissionDirection.GIVEN);
        ao0002.setPermissions(Set.of(DEBIT_CARD, VIEW, PAYMENT));
        ao0002.setStatus(AccountOperatorStatus.INACTIVE);
        result.add(ao0002);

        AccountOperator ao0003 = new AccountOperator();
        ao0003.setId(String.valueOf(++id));
        ao0003.setOperatorId("1");
        ao0003.setAccountId("343434343");
        ao0003.setDirection(PermissionDirection.GIVEN);
        ao0003.setPermissions(Set.of(DEBIT_CARD, VIEW, PAYMENT));
        ao0003.setStatus(AccountOperatorStatus.ACTIVE);
        result.add(ao0003);

        AccountOperator ao0004 = new AccountOperator();
        ao0004.setId(String.valueOf(++id));
        ao0004.setOperatorGroupId("2");
        ao0004.setAccountId("123123123");
        ao0004.setDirection(PermissionDirection.RECEIVED);
        ao0004.setPermissions(Set.of(VIEW, PAYMENT));
        ao0004.setStatus(AccountOperatorStatus.ACTIVE);
        result.add(ao0004);

        AccountOperator ao0005 = new AccountOperator();
        ao0005.setId(String.valueOf(++id));
        ao0005.setOperatorId("1");
        ao0005.setAccountId("123123123");
        ao0005.setDirection(PermissionDirection.GIVEN);
        ao0005.setPermissions(Set.of(VIEW, PAYMENT));
        ao0005.setStatus(AccountOperatorStatus.ACTIVE);
        result.add(ao0005);

        AccountOperator ao1010 = new AccountOperator();
        ao1010.setId(String.valueOf(++id));
        ao1010.setOperatorId("5");
        ao1010.setAccountId("101010101");
        ao1010.setDirection(PermissionDirection.GIVEN);
        ao1010.setPermissions(Set.of(VIEW, PAYMENT));
        ao1010.setStatus(AccountOperatorStatus.INACTIVE);
        result.add(ao0005);

        return result;
    }

    public static List<CreditCard> getCreditCards() {
        final var creditCard = new CreditCard();
        creditCard.setId("3333");
        creditCard.setStatus(CardStatus.ACTIVE);
        creditCard.setSequenceNumber("1");
        creditCard.setCardNumber("5075");
        creditCard.setSequenceNumber("1");
        creditCard.setCardHolder("Boromir");
        creditCard.setMonthlyLimit(new BigDecimal(3000));
        creditCard.setAccountId("123456789");
        return List.of(creditCard);
    }

    public static List<DebitCard> getDebitCards() {
        var dc1111 = new DebitCard();
        dc1111.setId("1111");
        dc1111.setStatus(CardStatus.ACTIVE);
        dc1111.setCardNumber("1234");
        dc1111.setSequenceNumber("5");
        dc1111.setCardHolder("Frodo Basggins");
        dc1111.setAtmLimit(new CardLimit(new BigDecimal(3000), PeriodUnit.WEEK));
        dc1111.setPosLimit(new CardLimit(new BigDecimal(50), PeriodUnit.MONTH));
        dc1111.setAccountId("123456789");
        dc1111.setContactless(true);

        var dc2222 = new DebitCard();
        dc2222.setId("2222");
        dc2222.setStatus(CardStatus.ACTIVE);
        dc2222.setCardNumber("6527");
        dc2222.setSequenceNumber("1");
        dc2222.setCardHolder("Aragorn");
        dc2222.setAtmLimit(new CardLimit(new BigDecimal(100), PeriodUnit.DAY));
        dc2222.setPosLimit(new CardLimit(new BigDecimal(10000), PeriodUnit.MONTH));
        dc2222.setContactless(true);
        dc2222.setAccountId("123456789");

        var dc4444 = new DebitCard();
        dc4444.setId("4444");
        dc4444.setStatus(CardStatus.ACTIVE);
        dc4444.setCardNumber("1111");
        dc4444.setSequenceNumber("32");
        dc4444.setCardHolder("Super duper employee");
        dc4444.setAtmLimit(new CardLimit(new BigDecimal(100), PeriodUnit.DAY));
        dc4444.setPosLimit(new CardLimit(new BigDecimal(10000), PeriodUnit.MONTH));
        dc4444.setContactless(false);
        dc4444.setAccountId("987654321");

        var dc5555 = new DebitCard();
        dc5555.setId("5555");
        dc5555.setStatus(CardStatus.BLOCKED);
        dc5555.setCardNumber("5678");
        dc5555.setSequenceNumber("5");
        dc5555.setCardHolder("Darth Vader");
        dc5555.setAtmLimit(new CardLimit(new BigDecimal(500), PeriodUnit.DAY));
        dc5555.setPosLimit(new CardLimit(new BigDecimal(10), PeriodUnit.MONTH));
        dc5555.setContactless(true);

        return List.of(dc1111, dc2222, dc4444, dc5555);
    }
}
