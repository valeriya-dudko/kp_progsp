package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(User user) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into user(login,pass_hash,id_role,is_blocked) " + "values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setBytes(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setBoolean(4, user.getIsBlocked());
            st = ps.executeUpdate();
            System.out.println("inserted user " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int update(User user) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update user set login=?,pass_hash=?,id_role=?,is_blocked=? where id_user=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setBytes(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setBoolean(4, user.getIsBlocked());
            ps.setLong(5, user.getId());
            st = ps.executeUpdate();
            System.out.println("updated user " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int delete(long id) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from user where id_user=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted user " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public User searchById(long id) {
        User user = new User();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from user where id_user=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getBytes("pass_hash"));
                user.setRole(rs.getInt("id_role"));
                user.setIsBlocked(rs.getBoolean("is_blocked"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }

    public User searchByLogin(String login) {
        User user = new User();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from user where login=?";
            ps = con.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getBytes("pass_hash"));
                user.setRole(rs.getInt("id_role"));
                user.setIsBlocked(rs.getBoolean("is_blocked"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }

    public List<User> viewAll() {
        List<User> userList = new ArrayList<User>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from user order by id_user";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getBytes("pass_hash"));
                user.setRole(rs.getInt("id_role"));
                user.setIsBlocked(rs.getBoolean("is_blocked"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }
}
