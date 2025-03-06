package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrierDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Carrier carrier) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into carrier(name,id_company) " + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, carrier.getName());
            ps.setLong(2, carrier.getCompany().getId());
            st = ps.executeUpdate();
            System.out.println("Добавлен перевозчик " + st);
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

    public int update(Carrier carrier) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update carrier set name=?,id_company=?" + "where id_carrier=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, carrier.getName());
            ps.setLong(2, carrier.getCompany().getId());
            ps.setLong(3, carrier.getId());
            st = ps.executeUpdate();
            System.out.println("Обновлен перевозчик " + st);
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
            String query = "delete from carrier where id_carrier=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("Удален перевозчик " + st);
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

    public Carrier searchById(long id) {
        Carrier carrier = new Carrier();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from carrier where id_carrier=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                carrier.setId(rs.getLong("id_carrier"));
                carrier.setName(rs.getString("name"));

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                carrier.setCompany(company);
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
        return carrier;
    }

    public List<Carrier> searchByName(String name) {
        List<Carrier> carrierList = new ArrayList<Carrier>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from carrier where name like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next())
            {
                Carrier carrier = new Carrier();
                carrier.setId(rs.getLong("id_carrier"));
                carrier.setName(rs.getString("name"));

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                carrier.setCompany(company);

                carrierList.add(carrier);
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
        return carrierList;
    }

    public List<Carrier> viewAll() {
        List<Carrier> carrierList = new ArrayList<Carrier>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from carrier order by id_carrier";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Carrier carrier = new Carrier();
                carrier.setId(rs.getLong("id_carrier"));
                carrier.setName(rs.getString("name"));

                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.searchById(rs.getLong("id_company"));
                carrier.setCompany(company);

                carrierList.add(carrier);
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
        return carrierList;
    }

}
