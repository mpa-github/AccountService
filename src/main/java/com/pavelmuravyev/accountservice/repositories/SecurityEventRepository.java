package com.pavelmuravyev.accountservice.repositories;

import com.pavelmuravyev.accountservice.models.SecurityEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityEventRepository extends CrudRepository<SecurityEvent, Long> {

    public List<SecurityEvent> findAllByOrderByIdAsc();
}
