package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.*;
import com.soge.katasoge.model.*;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceImplTest {

    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private AccountService mockAccountService;
    @Mock
    private ClientService mockClientService;
    @Mock
    private OperationRepository mockOperationRepository;

    private WithdrawalServiceImpl withdrawalServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        withdrawalServiceImplUnderTest = new WithdrawalServiceImpl(mockAccountRepository, mockAccountService, mockClientService, mockOperationRepository);
    }

    @Test
    void testWithdraw() throws Exception {
        // Setup
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
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        expectedResult.setCurrency(currency1);
        expectedResult.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        expectedResult.setOwner(owner);
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
        expectedResult.setOperations(Arrays.asList(operation1));

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(eq(0L))).thenReturn(client);

        // Configure AccountService.verifyAccount(...).
        final Account account = new Account();
        account.setId(0L);
        account.setBban("bban");
        account.setIban("iban");
        account.setBic("bic");
        final com.soge.katasoge.model.Country Country1 = new Country();
        Country1.setId(0L);
        Country1.setCountryCode("countryCode");
        Country1.setCountryName("countryName");
        account.setCountry(Country1);
        account.setAmount(new BigDecimal("0.00"));
        final Currency currency3 = new Currency();
        currency3.setId(0L);
        currency3.setCurrencyCode("currencyCode");
        currency3.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency3);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner1 = new Client();
        owner1.setId(0L);
        owner1.setFirstName("firstName");
        owner1.setLastName("lastName");
        owner1.setWithdrawalAllowed(false);
        owner1.setDepositAllowed(false);
        owner1.setBalanceAllowed(false);
        account.setOwner(owner1);
        final Operation operation2 = new Operation();
        operation2.setId(0L);
        operation2.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setAmount(new BigDecimal("0.00"));
        final Currency currency4 = new Currency();
        currency4.setId(0L);
        currency4.setCurrencyCode("currencyCode");
        currency4.setCurrencyLabel("currencyLabel");
        operation2.setCurrency(currency4);
        operation2.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation2));
        when(mockAccountService.verifyAccount(eq(0L))).thenReturn(account);

        // Configure OperationRepository.save(...).
        final Operation operation3 = new Operation();
        operation3.setId(0L);
        operation3.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation3.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation3.setAmount(new BigDecimal("0.00"));
        final Currency currency5 = new Currency();
        currency5.setId(0L);
        currency5.setCurrencyCode("currencyCode");
        currency5.setCurrencyLabel("currencyLabel");
        operation3.setCurrency(currency5);
        operation3.setSource(Source.CARD);
        when(mockOperationRepository.save(any(Operation.class))).thenReturn(operation3);

        // Configure AccountService.save(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setBban("bban");
        account1.setIban("iban");
        account1.setBic("bic");
        final com.soge.katasoge.model.Country Country2 = new Country();
        Country2.setId(0L);
        Country2.setCountryCode("countryCode");
        Country2.setCountryName("countryName");
        account1.setCountry(Country2);
        account1.setAmount(new BigDecimal("0.00"));
        final Currency currency6 = new Currency();
        currency6.setId(0L);
        currency6.setCurrencyCode("currencyCode");
        currency6.setCurrencyLabel("currencyLabel");
        account1.setCurrency(currency6);
        account1.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner2 = new Client();
        owner2.setId(0L);
        owner2.setFirstName("firstName");
        owner2.setLastName("lastName");
        owner2.setWithdrawalAllowed(false);
        owner2.setDepositAllowed(false);
        owner2.setBalanceAllowed(false);
        account1.setOwner(owner2);
        final Operation operation4 = new Operation();
        operation4.setId(0L);
        operation4.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation4.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation4.setAmount(new BigDecimal("0.00"));
        final Currency currency7 = new Currency();
        currency7.setId(0L);
        currency7.setCurrencyCode("currencyCode");
        currency7.setCurrencyLabel("currencyLabel");
        operation4.setCurrency(currency7);
        operation4.setSource(Source.CARD);
        account1.setOperations(Arrays.asList(operation4));
        when(mockAccountService.save(any(Account.class))).thenReturn(account1);

        final Account result = withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation);

        verify(mockOperationRepository, times(1)).save(any(Operation.class));
        verify(mockAccountService, times(1)).save(any(Account.class));

    }

    @Test
    void testWithdraw_ClientServiceThrowsClientNotFoundException() throws Exception {
        // Setup
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

        when(mockClientService.verifyClient(eq(0L))).thenThrow(ClientNotFoundException.class);

        // Run the test
        assertThatThrownBy(() -> withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation)).isInstanceOf(ClientNotFoundException.class);
         }

    @Test
    void testWithdraw_AccountServiceVerifyAccountThrowsAccountNotFoundException() throws Exception {
        // Setup
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

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(eq(0L))).thenReturn(client);

        when(mockAccountService.verifyAccount(eq(0L))).thenThrow(AccountNotFoundException.class);


        assertThatThrownBy(() -> withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation)).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void testWithdraw_AccountServiceCheckAccountIsOwnedByClientThrowsWrongAccountOwnerException() throws Exception {
        // Setup
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

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(eq(0L))).thenReturn(client);

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
        when(mockAccountService.verifyAccount(eq(0L))).thenReturn(account);

        doThrow(WrongAccountOwnerException.class).when(mockAccountService).checkAccountIsOwnedByClient(eq(0L), any(Account.class));

        assertThatThrownBy(() -> withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation)).isInstanceOf(WrongAccountOwnerException.class);
    }

    @Test
    void testWithdraw_UnhandledAmountException() throws Exception {
        // Setup
        final Operation operation = new Operation();
        operation.setId(0L);
        operation.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation.setAmount(new BigDecimal("10.00"));
        final Currency currency = new Currency();
        currency.setId(0L);
        currency.setCurrencyCode("currencyCode");
        currency.setCurrencyLabel("currencyLabel");
        operation.setCurrency(currency);
        operation.setSource(Source.CARD);

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
        expectedResult.setAmount(new BigDecimal("1.00"));
        final Currency currency1 = new Currency();
        currency1.setId(0L);
        currency1.setCurrencyCode("currencyCode");
        currency1.setCurrencyLabel("currencyLabel");
        expectedResult.setCurrency(currency1);
        expectedResult.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner = new Client();
        owner.setId(0L);
        owner.setFirstName("firstName");
        owner.setLastName("lastName");
        owner.setWithdrawalAllowed(false);
        owner.setDepositAllowed(false);
        owner.setBalanceAllowed(false);
        expectedResult.setOwner(owner);
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
        expectedResult.setOperations(Arrays.asList(operation1));

        // Configure ClientService.verifyClient(...).
        final Client client = new Client();
        client.setId(0L);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        client.setWithdrawalAllowed(false);
        client.setDepositAllowed(false);
        client.setBalanceAllowed(false);
        when(mockClientService.verifyClient(eq(0L))).thenReturn(client);

        // Configure AccountService.verifyAccount(...).
        final Account account = new Account();
        account.setId(0L);
        account.setBban("bban");
        account.setIban("iban");
        account.setBic("bic");
        final com.soge.katasoge.model.Country Country1 = new Country();
        Country1.setId(0L);
        Country1.setCountryCode("countryCode");
        Country1.setCountryName("countryName");
        account.setCountry(Country1);
        account.setAmount(new BigDecimal("0.00"));
        final Currency currency3 = new Currency();
        currency3.setId(0L);
        currency3.setCurrencyCode("currencyCode");
        currency3.setCurrencyLabel("currencyLabel");
        account.setCurrency(currency3);
        account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
        final Client owner1 = new Client();
        owner1.setId(0L);
        owner1.setFirstName("firstName");
        owner1.setLastName("lastName");
        owner1.setWithdrawalAllowed(false);
        owner1.setDepositAllowed(false);
        owner1.setBalanceAllowed(false);
        account.setOwner(owner1);
        final Operation operation2 = new Operation();
        operation2.setId(0L);
        operation2.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        operation2.setAmount(new BigDecimal("0.00"));
        final Currency currency4 = new Currency();
        currency4.setId(0L);
        currency4.setCurrencyCode("currencyCode");
        currency4.setCurrencyLabel("currencyLabel");
        operation2.setCurrency(currency4);
        operation2.setSource(Source.CARD);
        account.setOperations(Arrays.asList(operation2));
        when(mockAccountService.verifyAccount(eq(0L))).thenReturn(account);

        assertThatThrownBy(() -> withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation)).isInstanceOf(UnhandledAmountException.class);

    }
    @Test
    void testWithdraw_OperationNotYetSupportException() throws Exception {

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
            final Currency currency1 = new Currency();
            currency1.setId(0L);
            currency1.setCurrencyCode("currencyCode");
            currency1.setCurrencyLabel("currencyLabel");
            expectedResult.setCurrency(currency1);
            expectedResult.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
            final Client owner = new Client();
            owner.setId(0L);
            owner.setFirstName("firstName");
            owner.setLastName("lastName");
            owner.setWithdrawalAllowed(false);
            owner.setDepositAllowed(false);
            owner.setBalanceAllowed(false);
            expectedResult.setOwner(owner);
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
            expectedResult.setOperations(Arrays.asList(operation1));

            // Configure ClientService.verifyClient(...).
            final Client client = new Client();
            client.setId(0L);
            client.setFirstName("firstName");
            client.setLastName("lastName");
            client.setWithdrawalAllowed(false);
            client.setDepositAllowed(false);
            client.setBalanceAllowed(false);
            when(mockClientService.verifyClient(eq(0L))).thenReturn(client);

            // Configure AccountService.verifyAccount(...).
            final Account account = new Account();
            account.setId(0L);
            account.setBban("bban");
            account.setIban("iban");
            account.setBic("bic");
            final com.soge.katasoge.model.Country Country1 = new Country();
            Country1.setId(0L);
            Country1.setCountryCode("countryCode");
            Country1.setCountryName("countryName");
            account.setCountry(Country1);
            account.setAmount(new BigDecimal("0.00"));
            final Currency currency3 = new Currency();
            currency3.setId(1L);
            currency3.setCurrencyCode("otherCurrencyCode");
            currency3.setCurrencyLabel("otherCurrencyLabel");
            account.setCurrency(currency3);
            account.setTypeOfAccount(TypeOfAccount.CHECKING_ACCOUNT);
            final Client owner1 = new Client();
            owner1.setId(0L);
            owner1.setFirstName("firstName");
            owner1.setLastName("lastName");
            owner1.setWithdrawalAllowed(false);
            owner1.setDepositAllowed(false);
            owner1.setBalanceAllowed(false);
            account.setOwner(owner1);
            final Operation operation2 = new Operation();
            operation2.setId(0L);
            operation2.setInitiationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            operation2.setExecutedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
            operation2.setAmount(new BigDecimal("0.00"));
            final Currency currency4 = new Currency();
            currency4.setId(0L);
            currency4.setCurrencyCode("currencyCode");
            currency4.setCurrencyLabel("currencyLabel");
            operation2.setCurrency(currency4);
            operation2.setSource(Source.CARD);
            account.setOperations(Arrays.asList(operation2));
            when(mockAccountService.verifyAccount(eq(0L))).thenReturn(account);

            assertThatThrownBy(() -> withdrawalServiceImplUnderTest.withdraw(0L, 0L, operation)).isInstanceOf(OperationNotYetSupportException.class);

    }
}
