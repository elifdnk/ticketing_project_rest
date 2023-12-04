package com.cydeo.controller;

import com.cydeo.annotation.ExecutionTime;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController",description = "User API") // make up staff :)
public class UserController {

    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExecutionTime //I want to see what is execution time of this method
    @GetMapping
    @RolesAllowed({"Manager","Admin"})  //set up restrictions
    @Operation(summary = "Get Users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList =userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("users are successfully retrieves",userDTOList, HttpStatus.OK));
    }

    @ExecutionTime  //I want to see what is execution time of this method
    @GetMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Get User by username")
    public ResponseEntity<ResponseWrapper> getUserByName(@PathVariable("username") String username){
        UserDTO user = userService.findByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("user are successfully retrieve",user, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("user successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("user is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Delete User")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username) throws TicketingProjectException {
        userService.delete(username);
        return ResponseEntity.ok(new ResponseWrapper("user is successfully deleted",HttpStatus.OK));
    }



}
