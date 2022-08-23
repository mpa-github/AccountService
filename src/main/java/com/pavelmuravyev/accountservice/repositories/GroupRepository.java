package com.pavelmuravyev.accountservice.repositories;

import com.pavelmuravyev.accountservice.models.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    public Group findGroupByName(String groupName);
    public Optional<Group> findGroupByNameIgnoreCase(String groupName);
    public boolean existsGroupByNameIgnoreCase(String groupName);
}
