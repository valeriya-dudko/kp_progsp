package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeclarantDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Declarant declarant) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into declarant(name,id_company,id_user) " + "values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, declarant.getName());
            ps.setLong(2, declarant.getCompany().getId());
            ps.setLong(3, declarant.getUser().getId());
            st = ps.executeUpdate();
            System.out.println("inserted declarant " + st);
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

    public int update(Declarant declarant) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update declarant set name=?,id_company=?,id_user=? where id_deсlarant=?";
            ps = con.prepareStatement(query);
            ps.setString(1, declarant.getName());
            ps.setLong(2, declarant.getCompany().getId());
            ps.setLong(3, declarant.getUser().getId());
            ps.setLong(4, declarant.getId());
            st = ps.executeUpdate();
            System.out.println("updated declarant " + st);
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
            String query = "delete from declarant where id_declarant=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted declarant " + st);
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

    public Declarant searchById(long id) {
        Declarant declarant = new Declarant();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from declarant where id_deсlarant=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                declarant.setId(rs.getLong("id_deсlarant"));
                declarant.setName(rs.getString("name"));

                UserDao userDao = new UserDao();
                User user = userDao.searchById(rs.getLong("id_user"));
                declarant.setUser(user);

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                declarant.setCompany(company);
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
        return declarant;
    }

    public Declarant searchByUserId(long id) {
        Declarant declarant = new Declarant();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from declarant where id_user=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                declarant.setId(rs.getLong("id_deсlarant"));
                declarant.setName(rs.getString("name"));

                UserDao userDao = new UserDao();
                User user = userDao.searchById(rs.getLong("id_user"));
                declarant.setUser(user);

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                declarant.setCompany(company);
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
        return declarant;
    }

    public List<Declarant> searchByName(String name) {
        List<Declarant> declarantList = new ArrayList<Declarant>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from declarant where name like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Declarant declarant = new Declarant();
                declarant.setId(rs.getLong("id_declarant"));
                declarant.setName(rs.getString("name"));

                UserDao userDao = new UserDao();
                User user = userDao.searchById(rs.getLong("id_user"));
                declarant.setUser(user);

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                declarant.setCompany(company);

                declarantList.add(declarant);
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
        return declarantList;
    }

    public List<Declarant> viewAll() {
        List<Declarant> declarantList = new ArrayList<Declarant>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from declarant order by id_declarant";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Declarant declarant = new Declarant();
                declarant.setId(rs.getLong("id_declarant"));
                declarant.setName(rs.getString("name"));

                UserDao userDao = new UserDao();
                User user = userDao.searchById(rs.getLong("id_user"));
                declarant.setUser(user);

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                declarant.setCompany(company);

                declarantList.add(declarant);
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
        return declarantList;
    }
}
