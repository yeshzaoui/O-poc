package fr.olbati.owish.controller;

import fr.olbati.owish.bean.TokenBean;
import fr.olbati.owish.bean.UserReadBean;
import fr.olbati.owish.bean.UserWriteBean;
import fr.olbati.owish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/signin")
    public ResponseEntity<TokenBean> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.signin(username, password);
        TokenBean tokenBean = new TokenBean();
        tokenBean.setToken(token);
        return new ResponseEntity<>(tokenBean, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<TokenBean> signup(@RequestBody UserWriteBean userWriteBean) {
        String token = userService.signup(userWriteBean);
        TokenBean tokenBean = new TokenBean();
        tokenBean.setToken(token);
        return new ResponseEntity<>(tokenBean, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable String username) {
        userService.remove(username);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity<UserReadBean> whoami(HttpServletRequest req) {
        return new ResponseEntity<>(userService.whoami(req), HttpStatus.OK);
    }

}


