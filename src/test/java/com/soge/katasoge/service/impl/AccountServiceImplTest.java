package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.*;
import com.soge.katasoge.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository mockAccountRepository;

    private AccountServiceImpl accountServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        accountServiceImplUnderTest = new AccountServiceImpl(mockAccountRepository);
    }

    @Test
    void testVerifyAccount() throws Exception {
        // Setup
        final Account expectedResult = new Account();
        expectedResult.setId(0L);
        expectedResult.setBban("bban");
        expectedResult.setIban("iban");
        expectedResult.setBic("bic");
        final Country Country = new Country();
        Country.setId(0L);
        Country.setCountryCode("countryCode");
        Country.setCountryName("countryName");
        expectedResult.setCountry(Country);
        expectedResult.setAmount(new BigDecimal("0.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        expectedResult.setCurrency(currency);
        expectedResult.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        expectedResult.setOwner(owner);
        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("0.00"));
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency1);
        operation.setSource(Source.CARD);
        expectedResult.setOperations(Arrays.asList(operation));

        // Configure AccountRepository.findById(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setBban("bban");
        account1.setIban("iban");
        account1.setBic("bic");
        final com.soge.katasoge.model.Country Country1 = new Country();
        Country1.setId(0L);
        Country1.setCountryCode("countryCode");
        Country1.setCountryName("countryName");
        account1.setCountry(Country1);
        account1.setAmount(new BigDecimal("0.00"));
        final Currency currency2 = new Currency();
        currency2.setId(0L);
        currency2.setCurrencyCode("currencyCode");
        currency2.setCurrencyLabel("currencyLabel");
        account1.setCurrency(currency2);
        account1.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner1 = new Client();
        owner1.setId(0L);
        owner1.setFirstName("firstName");
        owner1.setLastName("lastName");
        owner1.setWithdrawalAllowed(false);
        owner1.setDepositAllowed(false);
        owner1.setBalanceAllowed(false);
        account1.setOwner(owner1);
        final Operation operation1 = new Operation();
        operation1.setId(0L);
        operation1.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setAmount(new BigDecimal("0.00"));
        final Currency currency3 = new Currency();
        currency3.setId(0L);
        currency3.setCurrencyCode("currencyCode");
        currency3.setCurrencyLabel("currencyLabel");
        operation1.setCurrency(currency3);
        operation1.setSource(Source.CARD);
        account1.setOperations(Arrays.asList(operation1));
        final Optional<Account> account = Optional.of(account1);
        when(mockAccountRepository.findById(0L)).thenReturn(account);

        final Account result = accountServiceImplUnderTest.verifyAccount(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testVerifyAccount_ThrowsAccountNotFoundException() {
        when(mockAccountRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> accountServiceImplUnderTest.verifyAccount(0L)).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void testCheckAccountIsOwnedByClient() throws Exception {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setBban("bban");
        account.setIban("iban");
        account.setBic("bic");
        final Country Country = new Country();
        Country.setId(0L);
        Country.setCountryCode("countryCode");
        Country.setCountryName("countryName");
        account.setCountry(Country);
        account.setAmount(new BigDecimal("0.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        account.setOwner(owner);
        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("0.00"));
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency1);
        operation.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation));

        accountServiceImplUnderTest.checkAccountIsOwnedByClient(0L, account);

    }

    @Test
    void testCheckAccountIsOwnedByClient_ThrowsWrongAccountOwnerException() {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setBban("bban");
        account.setIban("iban");
        account.setBic("bic");
        final Country Country = new Country();
        Country.setId(0L);
        Country.setCountryCode("countryCode");
        Country.setCountryName("countryName");
        account.setCountry(Country);
        account.setAmount(new BigDecimal("0.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(1L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        account.setOwner(owner);
        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("0.00"));
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency1);
        operation.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation));

        // Run the test
        assertThatThrownBy(() -> accountServiceImplUnderTest.checkAccountIsOwnedByClient(0L, account)).isInstanceOf(WrongAccountOwnerException.class);
    }

    @Test
    void testSave() {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setBban("bban");
        account.setIban("iban");
        account.setBic("bic");
        final Country Country = new Country();
        Country.setId(0L);
        Country.setCountryCode("countryCode");
        Country.setCountryName("countryName");
        account.setCountry(Country);
        account.setAmount(new BigDecimal("0.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        account.setOwner(owner);
        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("0.00"));
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency1);
        operation.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation));

        when(mockAccountRepository.save(any(Account.class))).thenReturn(account);

        final Account result = accountServiceImplUnderTest.save(account);

        verify(mockAccountRepository, times(1)).save(account);

    }
}
