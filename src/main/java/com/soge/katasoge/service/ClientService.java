package com.soge.katasoge.service;

import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.model.Client;

import java.util.Optional;

public interface ClientService {
    Client verifyClient(Long clientId) throws ClientNotFoundException;
}
