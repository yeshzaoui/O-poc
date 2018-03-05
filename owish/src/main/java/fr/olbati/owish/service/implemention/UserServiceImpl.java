package fr.olbati.owish.service.implemention;

import fr.olbati.owish.bean.UserReadBean;
import fr.olbati.owish.bean.UserWriteBean;
import fr.olbati.owish.entity.Role;
import fr.olbati.owish.entity.User;
import fr.olbati.owish.exception.CustomException;
import fr.olbati.owish.mapper.UserMapper;
import fr.olbati.owish.repository.UserRepository;
import fr.olbati.owish.security.JwtTokenProvider;
import fr.olbati.owish.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String signup(UserWriteBean userWriteBean) {
        if (!userRepository.existsByUsername(userWriteBean.getUsername())) {
            userWriteBean.setPassword(passwordEncoder.encode(userWriteBean.getPassword()));
            if (userWriteBean.getRoles() == null) {
               userWriteBean.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_CLIENT)));
            }
            userRepository.save(UserMapper.toEntity(userWriteBean));
            return jwtTokenProvider.createToken(userWriteBean.getUsername(), userWriteBean.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public UserReadBean search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return UserMapper.toBean(user);
    }

    @Override
    public UserReadBean whoami(HttpServletRequest req) {
        User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        return UserMapper.toBean(user);
    }

    @Override
    public void remove(String username) {
        userRepository.deleteByUsername(username);
    }


}
