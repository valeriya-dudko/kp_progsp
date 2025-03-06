package kp.service;

import kp.model.*;
import kp.dao.*;
import kp.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserService {
    public static boolean isValidUser(User user) {

        try {
            UserDao userDao = new UserDao();
            User dbUser = userDao.searchByLogin(user.getLogin());
            if(Arrays.equals(user.getPassword(), dbUser.getPassword()) && !dbUser.getIsBlocked())
            {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
}
