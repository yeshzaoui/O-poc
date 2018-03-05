package fr.olbati.owish.service;

import fr.olbati.owish.bean.UserReadBean;
import fr.olbati.owish.bean.UserWriteBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface UserService {

    public String signin(String username, String password);
    public String signup(UserWriteBean userWriteBean);
    public UserReadBean search(String username);
    public void remove(String username);
    public UserReadBean whoami(HttpServletRequest request);

}
