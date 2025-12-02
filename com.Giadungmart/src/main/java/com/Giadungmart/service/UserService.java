package com.giadungmart.service;

import com.giadungmart.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String Email);

}
