package ru.kata.springbootsecurityrestcontrollers.services;


import ru.kata.springbootsecurityrestcontrollers.models.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    List<Role> findAll();
}
