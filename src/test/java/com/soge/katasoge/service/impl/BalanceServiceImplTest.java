package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.*;
import com.soge.katasoge.model.Currency;
import com.soge.katasoge.repository.AccountRepository;
import com.soge.katasoge.repository.OperationRepository;
import com.soge.katasoge.service.AccountService;
import com.soge.katasoge.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {

    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private ClientService mockClientService;
    @Mock
    private OperationRepository mockOperationRepository;
    @Mock
    private AccountService mockAccountService;

    private BalanceServiceImpl balanceServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        balanceServiceImplUnderTest = new BalanceServiceImpl(mockAccountRepository, mockClientService, mockOperationRepository, mockAccountService);
    }

    @Test
    void testGetBalance() throws Exception {

        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("0.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency);
        operation.setSource(Source.CARD);
        final List<Operation> expectedResult = Arrays.asList(operation);


        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(0L)).thenReturn(client);

        // Configure AccountService.verifyAccount(...).
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
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency1);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        account.setOwner(owner);
        final Operation operation1 = new Operation();
        operation1.setId(0L);
        operation1.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setAmount(new BigDecimal("0.00"));
        final Currency currency2 = new Currency();
        currency2.setId(0L);
        currency2.setCurrencyCode("currencyCode");
        currency2.setCurrencyLabel("currencyLabel");
        operation1.setCurrency(currency2);
        operation1.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation1));
        when(mockAccountService.verifyAccount(0L)).thenReturn(account);

        // Configure OperationRepository.getOperationsByAccountId(...).
        final Operation operation2 = new Operation();
        operation2.setId(0L);
        operation2.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setAmount(new BigDecimal("0.00"));
        final Currency currency3 = new Currency();
        currency3.setId(0L);
        currency3.setCurrencyCode("currencyCode");
        currency3.setCurrencyLabel("currencyLabel");
        operation2.setCurrency(currency3);
        operation2.setSource(Source.CARD);
        final List<Operation> operations = Arrays.asList(operation2);
        when(mockOperationRepository.getOperationsByAccountId(0L)).thenReturn(operations);

        // Run the test
        final List<Operation> result = balanceServiceImplUnderTest.getBalance(0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    void testGetBalance_ClientServiceThrowsClientNotFoundException() throws Exception {

        when(mockClientService.verifyClient(0L)).thenThrow(ClientNotFoundException.class);

        assertThatThrownBy(() -> balanceServiceImplUnderTest.getBalance(0L, 0L)).isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    void testGetBalance_AccountServiceVerifyAccountThrowsAccountNotFoundException() throws Exception {
        // Setup

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(0L)).thenReturn(client);

        when(mockAccountService.verifyAccount(0L)).thenThrow(AccountNotFoundException.class);

        // Run the test
        assertThatThrownBy(() -> balanceServiceImplUnderTest.getBalance(0L, 0L)).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void testGetBalance_AccountServiceCheckAccountIsOwnedByClientThrowsWrongAccountOwnerException() throws Exception {
        // Setup

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(0L)).thenReturn(client);

        // Configure AccountService.verifyAccount(...).
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
        when(mockAccountService.verifyAccount(0L)).thenReturn(account);

        doThrow(WrongAccountOwnerException.class).when(mockAccountService).checkAccountIsOwnedByClient(eq(0L), any(Account.class));

        assertThatThrownBy(() -> balanceServiceImplUnderTest.getBalance(0L, 0L)).isInstanceOf(WrongAccountOwnerException.class);
    }

    @Test
    void testGetBalance_OperationRepositoryReturnsNoItems() throws Exception {
        // Setup
        final List<Operation> expectedResult =Collections.emptyList();

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(eq(0l))).thenReturn(client);

        // Configure AccountService.verifyAccount(...).
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
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency1);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        account.setOwner(owner);
        final Operation operation1 = new Operation();
        operation1.setId(0L);
        operation1.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation1.setAmount(new BigDecimal("0.00"));
        final Currency currency2 = new Currency();
        currency2.setId(0L);
        currency2.setCurrencyCode("currencyCode");
        currency2.setCurrencyLabel("currencyLabel");
        operation1.setCurrency(currency2);
        operation1.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation1));
        when(mockAccountService.verifyAccount(eq(0l))).thenReturn(account);

        when(mockOperationRepository.getOperationsByAccountId(eq(0l))).thenReturn(expectedResult);

        // Run the test
        final List<Operation> result = balanceServiceImplUnderTest.getBalance(0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllBalances() throws Exception {
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
        final List<Account> expectedResult = Arrays.asList(account);

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(0L)).thenReturn(client);

        // Configure AccountRepository.getAccountsByClientIdWithBalances(...).
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
        final List<Account> accounts = Arrays.asList(account1);
        when(mockAccountRepository.getAccountsByClientIdWithBalances(0L)).thenReturn(accounts);

        final List<Account> result = balanceServiceImplUnderTest.getAllBalances(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllBalances_ClientServiceThrowsClientNotFoundException() throws Exception {
        when(mockClientService.verifyClient(0L)).thenThrow(ClientNotFoundException.class);

        assertThatThrownBy(() -> balanceServiceImplUnderTest.getAllBalances(0L)).isInstanceOf(ClientNotFoundException.class);
    }
}
