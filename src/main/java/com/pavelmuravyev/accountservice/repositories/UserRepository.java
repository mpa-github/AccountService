package com.pavelmuravyev.accountservice.repositories;

import com.pavelmuravyev.accountservice.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findAllByOrderByIdAsc();
    public Optional<User> findUserByEmailIgnoreCase(String email);
    public void deleteUserByEmailIgnoreCase(String email);
    public boolean existsUserByEmailIgnoreCase(String email);
}
