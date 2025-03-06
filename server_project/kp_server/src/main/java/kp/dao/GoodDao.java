package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Good good) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into good(name,amount,weight,price,rate_type,rate,VAT,excise,is_import,is_confirmed,id_declarant) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, good.getName());
            ps.setInt(2, good.getAmount());
            ps.setDouble(3, good.getWeight());
            ps.setDouble(4, good.getPrice());
            ps.setInt(5, good.getRateType());
            ps.setDouble(6, good.getRate());
            ps.setInt(7, good.getVAT());
            ps.setDouble(8, good.getExcise());
            ps.setBoolean(9, good.getIsImport());
            ps.setBoolean(10, good.getIsConfirmed());
            ps.setLong(11, good.getDeclarant().getId());
            st = ps.executeUpdate();
            System.out.println("inserted good " + st);
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

    public int update(Good good) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update good set name=?,amount=?,weight=?,price=?,rate_type=?,rate=?,VAT=?," +
                    "excise=?,is_import=?,is_confirmed=?,id_declarant=? where id_good=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, good.getName());
            ps.setInt(2, good.getAmount());
            ps.setDouble(3, good.getWeight());
            ps.setDouble(4, good.getPrice());
            ps.setInt(5, good.getRateType());
            ps.setDouble(6, good.getRate());
            ps.setInt(7, good.getVAT());
            ps.setDouble(8, good.getExcise());
            ps.setBoolean(9, good.getIsImport());
            ps.setBoolean(10, good.getIsConfirmed());
            ps.setLong(11, good.getDeclarant().getId());
            ps.setLong(12, good.getId());
            st = ps.executeUpdate();
            System.out.println("updated good " + st);
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
            String query = "delete from good where id_good=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted good " + st);
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

    public List<Good> searchByName(String name) {
        List<Good> goodList = new ArrayList<Good>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from good where name like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Good good = new Good();
                good.setId(rs.getLong("id_good"));
                good.setName(rs.getString("name"));
                good.setAmount(rs.getInt("amount"));
                good.setWeight(rs.getDouble("weight"));
                good.setPrice(rs.getDouble("price"));
                good.setRateType(rs.getInt("rate_type"));
                good.setRate(rs.getDouble("rate"));
                good.setVAT(rs.getInt("VAT"));
                good.setExcise(rs.getDouble("excise"));
                good.setIsImport(rs.getBoolean("is_import"));
                good.setIsConfirmed(rs.getBoolean("is_confirmed"));

                DeclarantDao declarantDao = new DeclarantDao();
                Declarant declarant = declarantDao.searchById(rs.getLong("id_user"));
                good.setDeclarant(declarant);

                goodList.add(good);
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
        return goodList;
    }

    public Good searchById(long id) {
        Good good = new Good();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from good where id_good=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next())
            {
                good.setId(rs.getLong("id_good"));
                good.setName(rs.getString("name"));
                good.setAmount(rs.getInt("amount"));
                good.setWeight(rs.getDouble("weight"));
                good.setPrice(rs.getDouble("price"));
                good.setRateType(rs.getInt("rate_type"));
                good.setRate(rs.getDouble("rate"));
                good.setVAT(rs.getInt("VAT"));
                good.setExcise(rs.getDouble("excise"));
                good.setIsImport(rs.getBoolean("is_import"));
                good.setIsConfirmed(rs.getBoolean("is_confirmed"));

                DeclarantDao declarantDao = new DeclarantDao();
                Declarant declarant = declarantDao.searchById(rs.getLong("id_declarant"));
                good.setDeclarant(declarant);
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
        return good;
    }

    public List<Good> viewAll() {
        List<Good> goodList = new ArrayList<Good>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from good order by id_good";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getLong("id_good"));
                good.setName(rs.getString("name"));
                good.setAmount(rs.getInt("amount"));
                good.setWeight(rs.getDouble("weight"));
                good.setPrice(rs.getDouble("price"));
                good.setRateType(rs.getInt("rate_type"));
                good.setRate(rs.getInt("rate"));
                good.setVAT(rs.getInt("VAT"));
                good.setExcise(rs.getInt("excise"));
                good.setIsImport(rs.getBoolean("is_import"));
                good.setIsConfirmed(rs.getBoolean("is_confirmed"));

                DeclarantDao declarantDao = new DeclarantDao();
                Declarant declarant = declarantDao.searchById(rs.getLong("id_declarant"));
                good.setDeclarant(declarant);

                goodList.add(good);
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
        return goodList;
    }
}
