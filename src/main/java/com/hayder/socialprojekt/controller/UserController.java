package com.hayder.socialprojekt.controller;


import com.hayder.socialprojekt.jwt.JwtUtils;
import com.hayder.socialprojekt.model.ApplicationUser;
import com.hayder.socialprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping
    public List<ApplicationUser> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("{id}")
    public ApplicationUser getOneUser(@PathVariable long id){
        return userService.getOneUser(id);
    }
    @GetMapping("/inlogeduser")
    public ApplicationUser getlogedUser(@RequestHeader(name = "Authorization") String headerAuth){
        String token = headerAuth.substring(7);
        String username = jwtUtils.extractUsername(token);
        return userService.findUserByUsername(username);
    }
    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody ApplicationUser applicationUser){
        if (userService.loadUserByUsername(applicationUser.getEmail()) != null){
             return new ResponseEntity<String>("Username is busy!", HttpStatus.BAD_REQUEST);
        }
        userService.addUser(applicationUser);
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);
//        return userService.addUser(applicationUser);
    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }
    @PutMapping("{id}")
    public ApplicationUser updateUser(@PathVariable long id, @RequestBody ApplicationUser user){
        return userService.updateUser(id, user);
    }
    //UPLOAD PROFILE IMAGE
    @PostMapping(
            path = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void imagUpload(@RequestHeader(name = "Authorization") String httpHeader, @RequestParam MultipartFile file){
        String token = httpHeader.substring(7);
        String username = jwtUtils.extractUsername(token);
        userService.uploadUserProfileImage(username, file);
    }
    //SERVING THE PROFILE IMAGES
    @RequestMapping(value = "/img/{imagename}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String imagename) throws IOException {

        var imgFile = new ClassPathResource("images/" + imagename);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
    //GETTING USER BY TOKEN TO USE IN REACTJS TO IDENTIFY WHO THE USER IS
    @GetMapping("getInloggedUser")
    ApplicationUser getInloggedUser(@RequestHeader(name = "Authorization") String auth){
        return userService.findUserByUsername(jwtUtils.extractUsername(auth.substring(7)));
    }


}
