package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Post post) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into post(name,address) "
                    + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, post.getName());
            ps.setString(2, post.getAddress());
            st = ps.executeUpdate();
            System.out.println("inserted post " + st);
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

    public int update(Post post) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update post set name=?,address=?"
                    + "where id_post=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, post.getName());
            ps.setString(2, post.getAddress());
            ps.setLong(3, post.getId());
            st = ps.executeUpdate();
            System.out.println("updated post " + st);
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
            String query = "delete from post where id_post=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted post " + st);
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

    public Post searchById(long id) {
        Post post = new Post();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from post where id_post=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                post.setId(rs.getLong("id_post"));
                post.setName(rs.getString("name"));
                post.setAddress(rs.getString("address"));
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
        return post;
    }

    public Post searchByName(String name) {
        Post post = new Post();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from post where name=?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                post.setId(rs.getLong("id_post"));
                post.setName(rs.getString("name"));
                post.setAddress(rs.getString("address"));
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
        return post;
    }

    public List<Post> viewAll() {
        List<Post> postList = new ArrayList<Post>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from post order by id_post";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id_post"));
                post.setAddress(rs.getString("address"));
                post.setName(rs.getString("name"));
                postList.add(post);
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
        return postList;
    }
}
