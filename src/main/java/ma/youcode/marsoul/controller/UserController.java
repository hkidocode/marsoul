package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.UserDTO;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<UserDTO> getAllUsers() {
        Collection<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(modelMapper.map(allUsers, UserDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(modelMapper.map(user, UserDTO.class));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        User user = userService.saveUser(modelMapper.map(userDTO, User.class), userDTO.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id, modelMapper.map(userDTO, User.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
