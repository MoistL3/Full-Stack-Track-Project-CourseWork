package com.example.ecommerceapplication.controller;
import com.example.ecommerceapplication.PayLoad.LoginRequest;
import com.example.ecommerceapplication.PayLoad.LoginResponse;
import com.example.ecommerceapplication.Response.ApiResponse;
import com.example.ecommerceapplication.Security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class AuthController {
   private AuthenticationManager authenticationManager ;
   private JwtUtils jwtUtils ;
   public AuthController(AuthenticationManager authenticationManager
           , JwtUtils jwtUtils){
       this.authenticationManager = authenticationManager ;
       this.jwtUtils = jwtUtils ;
   }
    @PostMapping("/signin")
    public ResponseEntity<?> authenicateUser(@RequestBody LoginRequest request){
            Authentication authentication = null;
        try {
               authentication =  authenticationManager.authenticate(
                                       new UsernamePasswordAuthenticationToken(
                                            request.getUserName() , request.getPassword()
                                 )
                                 ) ;
        } catch (AuthenticationException e) {
            Map <String , Object> map =new HashMap<>() ;
            map.put("message" , "bad credentials") ;
            map.put("status" , false);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false , "security issue happened" , map));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal() ;
        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponse response = new LoginResponse(
                jwtToken  , request.getUserName(), roles
        ) ;
        return ResponseEntity.status(HttpStatus.OK)
                .body(response) ;
    }
}
