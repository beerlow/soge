package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.model.Client;
import com.soge.katasoge.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository mockClientRepository;

    private ClientServiceImpl clientServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        clientServiceImplUnderTest = new ClientServiceImpl(mockClientRepository);
    }

    @Test
    void testVerifyClient() throws Exception {
        // Setup
        final Client expectedResult = new Client();
        expectedResult.setId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setWithdrawalAllowed(false);
        expectedResult.setDepositAllowed(false);
        expectedResult.setBalanceAllowed(false);

        // Configure ClientRepository.findById(...).
        final Client client1 = new Client();
        client1.setId(0L);
        client1.setFirstName("firstName");
        client1.setLastName("lastName");
        client1.setWithdrawalAllowed(false);
        client1.setDepositAllowed(false);
        client1.setBalanceAllowed(false);
        final Optional<Client> client = Optional.of(client1);
        when(mockClientRepository.findById(0L)).thenReturn(client);

        final Client result = clientServiceImplUnderTest.verifyClient(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testVerifyClient_ThrowsClientNotFoundException() {
        when(mockClientRepository.findById(eq(1L))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientServiceImplUnderTest.verifyClient(1L)).isInstanceOf(ClientNotFoundException.class);
    }
}
