package ru.kata.springbootsecurityrestcontrollers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.springbootsecurityrestcontrollers.dao.RoleRepository;
import ru.kata.springbootsecurityrestcontrollers.models.Role;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return (Role) roleRepository.findByName(name);
    }
}
