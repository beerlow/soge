package com.soge.katasoge.repository;

import com.soge.katasoge.model.Client;

import java.util.Optional;


public interface ClientRepository {
    public Optional<Client> findById(long id);
}
