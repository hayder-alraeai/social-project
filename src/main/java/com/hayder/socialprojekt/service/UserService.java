package com.hayder.socialprojekt.service;
import com.hayder.socialprojekt.dao.UserRepository;
import com.hayder.socialprojekt.model.ApplicationUser;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.entity.ContentType;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ApplicationUser> getAllUsers(){
        return userRepository.findAll();
    }
    public ApplicationUser getOneUser(long id){
        return userRepository.findById(id).get();
    }
    public ApplicationUser addUser(ApplicationUser user){
        if (userRepository.findAll().stream().anyMatch(p -> p.getEmail().equalsIgnoreCase(user.getEmail()))){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
    public ApplicationUser updateUser(long id, ApplicationUser user){
        return userRepository.findById(id)
                .map(p -> {
                    p.setName(user.getName());
                    p.setUserProfileImage(user.getUserProfileImage());
                    return userRepository.save(p);
                })
                .orElseGet(() -> {
                    user.setUserId(id);
                    return userRepository.save(user);
                });
    }
    public ApplicationUser findUserByUsername(String username){
        return userRepository.findUserByEmail(username);
    }
    //IMPLIMENTATION OF THE USERDETAILSSERVICE TO USER IN LOGIN !!
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
    //UPLOAD A PROFILE BILD
    public void uploadUserProfileImage(String usernam, MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalStateException("this file is to big...");
        }
        UUID uuid = UUID.randomUUID();
        ApplicationUser user = findUserByUsername(usernam);
        //CHECK IF USER HAS A PROFILE IMAGE
        if (user.getUserProfileImage() == null){
            try{
                Path copyLocation = Paths
                        .get("src/main/resources/images" + File.separator + StringUtils.cleanPath(uuid + ".jpg"));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                user.setUserProfileImage(uuid + ".jpg");
                userRepository.save(user);
            }catch (Exception e){

                e.printStackTrace();
            }
        }else{
            try{
                Path copyLocation = Paths
                        .get("src/main/resources/images" + File.separator + StringUtils.cleanPath(user.getUserProfileImage()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }
}
