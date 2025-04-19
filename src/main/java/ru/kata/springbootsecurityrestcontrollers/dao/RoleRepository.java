package ru.kata.springbootsecurityrestcontrollers.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.springbootsecurityrestcontrollers.models.Role;


import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByName(String name);
}
