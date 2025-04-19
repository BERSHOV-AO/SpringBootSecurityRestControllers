package ru.kata.springbootsecurityrestcontrollers.services;


import ru.kata.springbootsecurityrestcontrollers.models.Role;

public interface RoleService {
    Role findByName(String name);
}
