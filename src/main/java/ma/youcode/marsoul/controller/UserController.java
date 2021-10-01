package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.constant.FileConstant;
import ma.youcode.marsoul.dto.UserDTO;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.message.MessageResponse;
import ma.youcode.marsoul.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/marsoul/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Pageable pagingSort = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> allUsers = userService.getAllUsers(pagingSort);
        return ResponseEntity.ok().body(allUsers);
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

    @PostMapping("/{id}/update-profile-image")
    public ResponseEntity<MessageResponse> updateProfileImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image) throws IOException {
        userService.updateProfileImage(id, image);
        return ResponseEntity.ok().body(new MessageResponse("Profile image updated successfully"));
    }

    @GetMapping(path = "/user/image/{first-name}-{last-name}/{file-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("first-name") String firstName, @PathVariable("last-name") String lastName,
                                  @PathVariable("file-name") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(FileConstant.USER_FOLDER + firstName + "-" + lastName + "/" + fileName));
    }

    @GetMapping(path = "/user/image/profile/{first-name}-{last-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getTemporaryProfileImage(@PathVariable("first-name") String firstName, @PathVariable("last-name") String lastName) throws IOException {
        URL url = new URL(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + firstName + "-" + lastName);
        // place to store image coming from API
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()){
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead); // 0 - 1024 bytes
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

}
