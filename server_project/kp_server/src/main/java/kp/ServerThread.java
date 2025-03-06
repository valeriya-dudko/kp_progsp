package kp;

import kp.dao.*;
import kp.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import kp.service.*;

public class ServerThread extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private User currUser;
    private Declarant currDeclarant;

    private List<Good> currGoods;

    public ServerThread(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            closeAllStreams(socket, out, in);
        }

    }

    public void closeAllStreams(Socket socket, ObjectOutputStream out, ObjectInputStream in)
    {
        try
        {
            if(out != null)
            {
                out.close();
            }
            if(in != null)
            {
                in.close();
            }
            if(socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendObjToClient(Object obj)
    {
        try
        {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            closeAllStreams(socket, out, in);
        }
    }

    @Override
    public void run() {
        System.out.println("Клиент подключен.");
        String choice;
        String response;
        boolean isLogged = false;

        try
        {
            choice = " ";
            UserDao userDao = new UserDao();
            DeclarantDao declDao = new DeclarantDao();
            GoodDao goodDao = new GoodDao();
            PostDao postDao = new PostDao();
            CarrierDao carrierDao = new CarrierDao();
            PassingDao passingDao = new PassingDao();
            CompanyDao compDao = new CompanyDao();


            while(choice != null)
            {
                while(isLogged == false)
                {
                    choice = (String) in.readObject();
                    if(choice.equals("LOGIN"))
                    {
                        User user;
                        user = (User) in.readObject();
                        System.out.println(user.getLogin());
                        if (UserService.isValidUser(user) && user.getIsBlocked() == false)
                        {
                            response = "OK";
                            out.writeObject(response);
                            currUser = userDao.searchByLogin(user.getLogin());
                            System.out.println(currUser.getLogin());
                            sendObjToClient(currUser);
                            if(currUser.getRole() == 3)
                            {
                                currDeclarant = declDao.searchByUserId(currUser.getId());
                            }
                            //System.out.println(currDeclarant.getName());
                            //currUser = user;
                            isLogged = true;

                        }
                        else
                        {
                            response = "ERROR";
                            out.writeObject(response);
                            System.out.println("Неправильный логин или пароль.");
                        }
                    }
                    else if(choice.equals("REGISTER"))
                    {
                        //DeclarantDao declDao = new DeclarantDao();
                        Declarant declarant = (Declarant) in.readObject();
                        declarant.getUser().setIsBlocked(false);

                        if(userDao.searchByLogin(declarant.getUser().getLogin()).getLogin() != null)
                        {
                            response = "ERROR";
                            out.writeObject(response);
                            System.out.println("Такой пользователь уже существует.");
                        }
                        else {
                            response = "OK";
                            out.writeObject(response);
                            userDao.insert(declarant.getUser());
                            User dbUser = userDao.searchByLogin(declarant.getUser().getLogin());
                            declarant.setUser(dbUser);
                            declDao.insert(declarant);
                        }
                    }
                    else if(choice.equals("PRE_REGISTER"))
                    {
                        List<Company> allComp = compDao.viewAll();
                        Integer count = allComp.size();

                        sendObjToClient(count);

                        for (Company company : allComp) {
                            sendObjToClient(company);
                        }
                    }
                }
                while(isLogged == true)
                {
                    System.out.println("Клиент вошел в учетную запись.");
                    choice = (String) in.readObject();
                    switch (choice)
                    {
                        case "LOGOUT":
                        {
                            System.out.println("Клиент вышел из учетной записи.");
                            currUser = null;
                            currDeclarant = null;
                            isLogged = false;
                            break;
                        }
                        case "EDIT_DECLARANT":
                        {
                            List<Company> allComp = compDao.viewAll();
                            Integer count = allComp.size();

                            sendObjToClient(count);

                            for (Company company : allComp) {
                                sendObjToClient(company);
                            }

                            System.out.println("Работаем с декларантом...");
                            Declarant declarant = (Declarant) in.readObject();
                            DeclarantService declarantService = new DeclarantService();
                            declarant.setUser(currUser);

                            declarant.setId(declDao.searchByUserId(declarant.getUser().getId()).getId());
                            declDao.update(declarant);

                            out.writeObject("OK");
                            break;
                        }
                        case "DECLARE_GOODS":
                        {
                            if(((String) in.readObject()).equals("ERROR"))
                            {
                                break;
                            }
                            sendObjToClient(currDeclarant);

                            Good good = (Good) in.readObject();
                            goodDao.insert(good);
                            break;
                        }
                        case "TO_CALC_TAX":
                        {
                            List<Good> goods = new ArrayList<Good>();

                            List<Good> dbGoods = goodDao.viewAll();

                            for (Good good : dbGoods)
                            {
                                if (!good.getIsConfirmed())
                                {
                                    goods.add(good);
                                }
                            }

                            sendObjToClient(goods.size());

                            for(Good good : goods)
                            {
                                sendObjToClient(good);
                            }

                            currGoods = goods;
                            break;
                        }
                        case "CALC_TAX":
                        {
                            Double finalTax = 0.;
                            for(Good good : currGoods)
                            {
                                finalTax = finalTax + GoodService.calcTax(good);
                            }

                            sendObjToClient(finalTax);
                            break;
                        }
                        case "ADD_POST":
                        {
                            Post post = (Post) in.readObject();
                            postDao.insert(post);
                            break;
                        }
                        case "VIEW_POST":
                        {
                            List<Post> posts = postDao.viewAll();

                            sendObjToClient(posts.size());

                            for(Post post : posts)
                            {
                                sendObjToClient(post);
                            }
                            break;
                        }
                        case "EDIT_POST":
                        {
                            List<Post> posts = postDao.viewAll();

                            sendObjToClient(posts.size());

                            for(Post post : posts)
                            {
                                sendObjToClient(post);
                            }

                            Long id = (Long) in.readObject();
                            String s = (String) in.readObject();
                            Post post = (Post) in.readObject();
                            post.setId(id);

                            postDao.update(post);
                            break;
                        }
                        case "DELETE_POST":
                        {
                            List<Post> posts = postDao.viewAll();

                            sendObjToClient(posts.size());

                            for(Post post : posts)
                            {
                                sendObjToClient(post);
                            }

                            Long id = (Long) in.readObject();

                            postDao.delete(id);
                            break;
                        }
                        case "SEARCH_POST":
                        {
                            String name = (String)in.readObject();
                            Post post = postDao.searchByName(name);

                            if(post.getName() != null)
                            {
                                sendObjToClient("Название: " + post.getName() + ", адрес: " + post.getAddress() + ".");
                            }
                            else
                            {
                                sendObjToClient("Пограничный пункт не найден.");
                            }
                            break;
                        }
                        case "TO_ADD_PASSING":
                        {
                            List<Post> posts = postDao.viewAll();

                            sendObjToClient(posts.size());

                            for(Post post : posts)
                            {
                                sendObjToClient(post);
                            }

                            List<Carrier> carriers = carrierDao.viewAll();

                            sendObjToClient(carriers.size());

                            for(Carrier carrier : carriers)
                            {
                                sendObjToClient(carrier);
                            }
                            break;

                        }
                        case "ADD_PASSING":
                        {
                            Passing passing = (Passing) in.readObject();
                            passingDao.insert(passing);
                            break;
                        }
                        case "VIEW_PASSING":
                        {
                            List<Passing> passings = passingDao.viewAll();

                            sendObjToClient(passings.size());

                            for(Passing passing : passings)
                            {
                                sendObjToClient(passing);
                            }
                            break;
                        }
                        case "UPDATE_PASSING":
                        {
                            List<Passing> passings = passingDao.viewAll();

                            sendObjToClient(passings.size());

                            for(Passing passing : passings)
                            {
                                sendObjToClient(passing);
                            }

                            List<Post> posts = postDao.viewAll();

                            sendObjToClient(posts.size());

                            for(Post post : posts)
                            {
                                sendObjToClient(post);
                            }

                            List<Carrier> carriers = carrierDao.viewAll();

                            sendObjToClient(carriers.size());

                            for(Carrier carrier : carriers)
                            {
                                sendObjToClient(carrier);
                            }
                            break;
                        }
                        case "EDITING_PASSING":
                        {
                            Passing passing = (Passing) in.readObject();
                            passingDao.update(passing);
                            break;

                        }
                        case "TO_DELETE_PASSING":
                        {
                            List<Passing> passings = passingDao.viewAll();

                            sendObjToClient(passings.size());

                            for(Passing passing : passings)
                            {
                                sendObjToClient(passing);
                            }
                            break;
                        }
                        case "DELETE_PASSING":
                        {
                            Long id = (Long) in.readObject();
                            passingDao.delete(id);
                            break;
                        }
                        case "FILTER_PASSING":
                        {
                            Date arr1 = (Date) in.readObject();
                            Date arr2 = (Date) in.readObject();

                            List<Passing> passings = passingDao.searchByRangeArrival(arr1, arr2);

                            sendObjToClient(passings.size());

                            for(Passing passing : passings)
                            {
                                sendObjToClient(passing);
                            }
                            break;

                        }
                        case "VIEW_GOOD":
                        {
                            List<Good> allGoods = goodDao.viewAll();
                            List<Good> goods = new ArrayList<>();

                            for(Good good : allGoods)
                            {
                                if(good.getIsConfirmed())
                                {
                                    goods.add(good);
                                }
                            }

                            sendObjToClient(goods.size());

                            for(Good good : goods)
                            {
                                sendObjToClient(good);
                            }
                            break;
                        }
                        case "CONFIRM_GOOD":
                        {
                            List<Good> allGoods = goodDao.viewAll();
                            List<Good> goods = new ArrayList<>();

                            for(Good good : allGoods)
                            {
                                if(!good.getIsConfirmed())
                                {
                                    goods.add(good);
                                }
                            }

                            sendObjToClient(goods.size());

                            for(Good good : goods)
                            {
                                sendObjToClient(good);
                            }

                            if(((String) in.readObject()).equals("BACK"))
                            {
                                break;
                            }
                            else {
                                Long id = (Long) in.readObject();
                                Good good = goodDao.searchById(id);
                                good.setIsConfirmed(true);
                                goodDao.update(good);
                                break;
                            }
                        }
                        case "DIAGRAM_GOOD":
                        {
                            List<Good> allGoods = goodDao.viewAll();
                            List<Good> goods = new ArrayList<>();

                            for(Good good : allGoods)
                            {
                                if(good.getIsConfirmed())
                                {
                                    goods.add(good);
                                }
                            }

                            sendObjToClient(goods.size());

                            for(Good good : goods)
                            {
                                sendObjToClient(good);
                            }
                            break;
                        }
                        case "BLOCK_USER":
                        {
                            List<User> allUsers = userDao.viewAll();
                            List<User> users = new ArrayList<>();

                            for(User user : allUsers)
                            {
                                if (user.getId() != currUser.getId() && !user.getIsBlocked())
                                {
                                    users.add(user);
                                }
                            }

                            sendObjToClient(users.size());

                            for(User user : users)
                            {
                                sendObjToClient(user);
                            }

                            Long id = (Long) in.readObject();
                            User blockedUser = userDao.searchById(id);
                            blockedUser.setIsBlocked(true);
                            userDao.update(blockedUser);
                            break;

                        }
                        case "UNBLOCK_USER":
                        {
                            List<User> allUsers = userDao.viewAll();
                            List<User> users = new ArrayList<>();

                            for(User user : allUsers)
                            {
                                if (user.getId() != currUser.getId() && user.getIsBlocked())
                                {
                                    users.add(user);
                                }
                            }

                            sendObjToClient(users.size());

                            for(User user : users)
                            {
                                sendObjToClient(user);
                            }

                            Long id = (Long) in.readObject();
                            User unblockedUser = userDao.searchById(id);
                            unblockedUser.setIsBlocked(false);
                            userDao.update(unblockedUser);
                            break;

                        }
                        case "DELETE_USER":
                        {
                            List<User> allUsers = userDao.viewAll();
                            List<User> users = new ArrayList<>();

                            for(User user : allUsers)
                            {
                                if (user.getId() != currUser.getId())
                                {
                                    users.add(user);
                                }
                            }

                            sendObjToClient(users.size());

                            for(User user : users)
                            {
                                sendObjToClient(user);
                            }

                            Long id = (Long) in.readObject();
                            userDao.delete(id);
                            break;
                        }
                        case "REGISTER_ADMIN":
                        {
                            User user = (User) in.readObject();

                            if(userDao.searchByLogin(user.getLogin()).getLogin() != null)
                            {
                                response = "ERROR";
                                out.writeObject(response);
                                System.out.println("Такой пользователь уже существует.");
                            }
                            else {
                                response = "OK";
                                out.writeObject(response);
                                userDao.insert(user);
                            }
                            break;
                        }
                        case "VIEW_USER":
                        {
                            List<User> allUsers = userDao.viewAll();

                            sendObjToClient(allUsers.size());

                            for(User user : allUsers)
                            {
                                sendObjToClient(user);
                            }
                            break;
                        }
                        case "ADD_CARRIER":
                        {
                            List<Company> comps = compDao.viewAll();

                            sendObjToClient(comps.size());

                            for(Company comp : comps)
                            {
                                sendObjToClient(comp);
                            }

                            Carrier carrier = (Carrier) in.readObject();
                            carrierDao.insert(carrier);
                            break;
                        }
                        case "VIEW_CARRIER":
                        {
                            List<Carrier> carriers = carrierDao.viewAll();

                            sendObjToClient(carriers.size());

                            for(Carrier carrier : carriers)
                            {
                                sendObjToClient(carrier);
                            }
                            break;
                        }
                        case "EDIT_CARRIER":
                        {
                            List<Carrier> carrs = carrierDao.viewAll();

                            sendObjToClient(carrs.size());

                            for(Carrier carrier : carrs)
                            {
                                sendObjToClient(carrier);
                            }

                            Long id = (Long) in.readObject();
                            String s = (String) in.readObject();
                            Carrier carrier = (Carrier)in.readObject();
                            carrier.setId(id);

                            carrierDao.update(carrier);
                            break;
                        }
                        case "DELETE_CARRIER":
                        {
                            List<Carrier> carrs = carrierDao.viewAll();

                            sendObjToClient(carrs.size());

                            for(Carrier carr : carrs)
                            {
                                sendObjToClient(carr);
                            }

                            Long id = (Long) in.readObject();

                            carrierDao.delete(id);
                            break;
                        }
                        case "SEARCH_CARRIER":
                        {
                            String name = (String)in.readObject();
                            List<Carrier> carrs = carrierDao.searchByName(name);

                            sendObjToClient(carrs.size());

                            for(Carrier carr : carrs)
                            {
                                sendObjToClient(carr);
                            }
                            break;
                        }
                        case "ADD_COMP":
                        {
                            Company comp = (Company) in.readObject();
                            compDao.insert(comp);
                            break;
                        }
                        case "VIEW_COMP":
                        {
                            List<Company> comps = compDao.viewAll();

                            sendObjToClient(comps.size());

                            for(Company comp : comps)
                            {
                                sendObjToClient(comp);
                            }
                            break;
                        }
                        case "EDIT_COMP":
                        {
                            List<Company> comps = compDao.viewAll();

                            sendObjToClient(comps.size());

                            for(Company comp : comps)
                            {
                                sendObjToClient(comp);
                            }

                            Long id = (Long) in.readObject();
                            String s = (String) in.readObject();
                            Company comp = (Company) in.readObject();
                            comp.setId(id);

                            compDao.update(comp);
                            break;
                        }
                        case "DELETE_COMP":
                        {
                            List<Company> comps = compDao.viewAll();

                            sendObjToClient(comps.size());

                            for(Company comp : comps)
                            {
                                sendObjToClient(comp);
                            }

                            Long id = (Long) in.readObject();

                            compDao.delete(id);
                            break;
                        }
                        case "SEARCH_COMP":
                        {
                            String name = (String)in.readObject();
                            Company comp = compDao.searchByName(name);

                            if(comp.getName() != null)
                            {
                                sendObjToClient("Название: " + comp.getName() + ", адрес: " + comp.getAddress() + ".");
                            }
                            else
                            {
                                sendObjToClient("Компания не найдена.");
                            }
                            break;

                        }

                    }

                }

            }
        } catch (Exception e) {
            System.out.println("Отключение...");
            closeAllStreams(socket, out, in);
        }

    }
}
