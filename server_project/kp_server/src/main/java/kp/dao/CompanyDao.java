package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Company company) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into company(name,address) "
                    + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, company.getName());
            ps.setString(2, company.getAddress());
            st = ps.executeUpdate();
            System.out.println("inserted company " + st);
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

    public int update(Company company) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update company set name=?,address=?"
                    + "where id_company=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, company.getName());
            ps.setString(2, company.getAddress());
            ps.setLong(3, company.getId());
            st = ps.executeUpdate();
            System.out.println("updated company " + st);
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
            String query = "delete from company where id_company=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted company " + st);
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

    public Company searchById(long id) {
        Company company = new Company();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from company where id_company=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                company.setId(rs.getLong("id_company"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
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
        return company;
    }

    public Company searchByName(String name) {
        Company company = new Company();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from company where name=?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                company.setId(rs.getLong("id_company"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
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
        return company;
    }

    public List<Company> viewAll() {
        List<Company> companyList = new ArrayList<Company>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from company order by id_company";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong("id_company"));
                company.setAddress(rs.getString("address"));
                company.setName(rs.getString("name"));
                companyList.add(company);
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
        return companyList;
    }
}
