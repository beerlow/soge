package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.model.Client;
import com.soge.katasoge.repository.ClientRepository;
import com.soge.katasoge.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client verifyClient(Long clientId) throws ClientNotFoundException {
        if(clientId ==null){
            throw new ClientNotFoundException();
        }
        return findById(clientId).orElseThrow(() -> new ClientNotFoundException());
    }


}
