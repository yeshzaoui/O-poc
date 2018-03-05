package fr.olbati.owish.mapper;

import fr.olbati.owish.bean.UserReadBean;
import fr.olbati.owish.bean.UserWriteBean;
import fr.olbati.owish.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserReadBean toBean(User user) {
        UserReadBean userReadBean = new UserReadBean();
        userReadBean.setId(user.getId());
        userReadBean.setFirstName(user.getFirstName());
        userReadBean.setLastName(user.getLastName());
        userReadBean.setUsername(user.getUsername());
        userReadBean.setRoles(user.getRoles());
        return userReadBean;
    }

    public static User toEntity(UserWriteBean userWriteBean) {
        User user = new User();
        user.setFirstName(userWriteBean.getFirstName());
        user.setLastName(userWriteBean.getLastName());
        user.setUsername(userWriteBean.getUsername());
        user.setPassword(userWriteBean.getPassword());
        user.setRoles(userWriteBean.getRoles());
        return user;
    }

    public static List<UserReadBean> toBeans (List<User> users) {
        List<UserReadBean> userReadBeans = new ArrayList<>();
        for (User user : users) {
            userReadBeans.add(UserMapper.toBean(user));
        }
        return userReadBeans;
    }

}
