package com.example.backendfinalProject.controllers.impl;
import com.example.backendfinalProject.DTOs.RoleToUserDTO;
import com.example.backendfinalProject.controllers.interfaces.RoleControllerInterface;
import com.example.backendfinalProject.models.user.Role;
import com.example.backendfinalProject.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * RESTful API for Role management
 */
@RestController
@RequestMapping("/api")
public class RoleController implements RoleControllerInterface {

    /**
     * User service for accessing user data
     */
    @Autowired
    private UserServiceInterface userService;

    /**
     * Save a new role
     *
     * @param role role to be saved
     */
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRole(@RequestBody Role role) {
        userService.saveRole(role);
    }

    /**
     * Add a role to a user
     *
     * @param roleToUserDTO DTO containing the username and role name
     */
    @PostMapping("/roles/addtouser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO) {
        userService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
    }
}
