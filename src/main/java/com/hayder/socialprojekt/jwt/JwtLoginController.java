package com.hayder.socialprojekt.jwt;

import com.hayder.socialprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/login")
public class JwtLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping
    public String login(@RequestBody RequestAuthenticationModel requestAuthenticationModel, HttpServletResponse response) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               requestAuthenticationModel.getUsername(), requestAuthenticationModel.getPassword()
            ));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(requestAuthenticationModel.getUsername());
        final String token = jwtUtils.generateToken(userDetails);
        response.addHeader("Authorization", "Bearer " + token);
        return new ResponseAuthenticationModel(token).getToken();
    }
}
