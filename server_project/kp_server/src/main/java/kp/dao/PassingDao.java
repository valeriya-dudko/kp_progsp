package kp.dao;

import kp.model.*;
import kp.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class PassingDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    public int insert(Passing passing) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into passing(arrival_date,departure_date,id_post,id_carrier) " + "values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setDate(1, passing.getArrivalDate());
            ps.setDate(2, passing.getDepartureDate());
            ps.setLong(3, passing.getPost().getId());
            ps.setLong(4, passing.getCarrier().getId());
            st = ps.executeUpdate();
            System.out.println("inserted passing " + st);
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

    public int update(Passing passing) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "update passing set arrival_date=?,departure_date=?,id_post=?,id_carrier=? where id_passing=? ";
            ps = con.prepareStatement(query);
            ps.setDate(1, passing.getArrivalDate());
            ps.setDate(2, passing.getDepartureDate());
            ps.setLong(3, passing.getPost().getId());
            ps.setLong(4, passing.getCarrier().getId());
            ps.setLong(5, passing.getId());
            st = ps.executeUpdate();
            System.out.println("updated passing " + st);
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
            String query = "delete from passing where id_passing=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted passing " + st);
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

    public Passing searchById(long id) {
        Passing passing = new Passing();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from passing where id_passing=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                passing.setId(rs.getLong("id_passing"));
                passing.setArrivalDate(rs.getDate("arrival_date"));
                passing.setDepartureDate(rs.getDate("departure_date"));

                PostDao postDao = new PostDao();
                Post post = postDao.searchById(rs.getLong("id_post"));
                passing.setPost(post);

                CarrierDao carrierDao = new CarrierDao();
                Carrier carrier = carrierDao.searchById(rs.getLong("id_carrier"));
                passing.setCarrier(carrier);
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
        return passing;
    }

    public List<Passing> searchByRangeArrival(Date arrival1, Date arrival2) {
        List<Passing> passingList = new ArrayList<Passing>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from passing where arrival_date between ? and ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, arrival1);
            ps.setDate(2, arrival2);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Passing passing = new Passing();
                passing.setId(rs.getLong("id_passing"));
                passing.setArrivalDate(rs.getDate("arrival_date"));
                passing.setDepartureDate(rs.getDate("departure_date"));

                PostDao postDao = new PostDao();
                Post post = postDao.searchById(rs.getLong("id_post"));
                passing.setPost(post);

                CarrierDao carrierDao = new CarrierDao();
                Carrier carrier = carrierDao.searchById(rs.getLong("id_carrier"));
                passing.setCarrier(carrier);

                passingList.add(passing);
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
        return passingList;
    }

    public List<Passing> searchByRangeDeparture(Date departure1, Date departure2) {
        List<Passing> passingList = new ArrayList<Passing>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from passing where departure_date between ? and ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, departure1);
            ps.setDate(2, departure2);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Passing passing = new Passing();
                passing.setId(rs.getLong("id_passing"));
                passing.setArrivalDate(rs.getDate("arrival_date"));
                passing.setDepartureDate(rs.getDate("departure_date"));

                PostDao postDao = new PostDao();
                Post post = postDao.searchById(rs.getLong("id_post"));
                passing.setPost(post);

                CarrierDao carrierDao = new CarrierDao();
                Carrier carrier = carrierDao.searchById(rs.getLong("id_carrier"));
                passing.setCarrier(carrier);

                passingList.add(passing);
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
        return passingList;
    }

    public List<Passing> viewAll() {
        List<Passing> passingList = new ArrayList<Passing>();

        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from passing order by id_passing";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Passing passing = new Passing();
                passing.setId(rs.getLong("id_passing"));
                passing.setArrivalDate(rs.getDate("arrival_date"));
                passing.setDepartureDate(rs.getDate("departure_date"));

                PostDao postDao = new PostDao();
                Post post = postDao.searchById(rs.getLong("id_post"));
                passing.setPost(post);

                CarrierDao carrierDao = new CarrierDao();
                Carrier carrier = carrierDao.searchById(rs.getLong("id_carrier"));
                passing.setCarrier(carrier);

                passingList.add(passing);
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
        return passingList;
    }
}
