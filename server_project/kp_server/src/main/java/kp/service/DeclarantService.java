package kp.service;

import kp.model.*;
import kp.dao.*;

import java.util.List;

public class DeclarantService {
    public boolean isDeclarantExist(Declarant currDeclarant) {
        boolean exist = false;

        try {
//            DeclarantDao declDao = new DeclarantDao();
//            User currUser = currDeclarant.getUser();
//            long currUserId = currUser.getId();
//            UserDao userDao = new UserDao();
//            System.out.println(currUser.getLogin());
//            User dbUser = userDao.searchByLogin(currUser.getLogin());
//            Declarant dbDeclarant = declDao.searchByUserId(currUserId);

            User currUser = currDeclarant.getUser();
            UserDao userDao = new UserDao();
            CompanyDao companyDao = new CompanyDao();
            long userId = userDao.searchByLogin(currUser.getLogin()).getId();
            System.out.println(userId);
            DeclarantDao declDao = new DeclarantDao();
            Declarant declarant = declDao.searchByUserId(userId);

            //Declarant dbDeclarant = declDao.searchByUserId(currUser.getId());//это говнище убрать, заменить на адекватные функции дао

            System.out.println(declarant.getId());


            if(declarant.getId() != 0)
            {
                exist = true;
            }

            //declDao.searchB(user.getId());

//            List<Declarant> declarantList = declDao.searchByName(currDeclarant.getName());
//
//            for (int i = 0; i < declarantList.size(); i++)
//            {
//                if(declarantList.get(i).getCompany().getName().equals(currDeclarant.getCompany().getName()))
//                {
//                    exist = true;
//                    break;
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(exist);

        return exist;
    }

    public static Declarant restoreDeclarantAll(Declarant declarant)
    {
        Declarant restoredDeclarant = new Declarant();
        DeclarantDao declDao = new DeclarantDao();
        User user = declarant.getUser();
        UserDao userDao = new UserDao();
        userDao.searchByLogin(user.getLogin());
        //declDao.searchB(user.getId());

        List<Declarant> declarantList = declDao.searchByName(declarant.getName());

        for (int i = 0; i < declarantList.size(); i++)
        {
            if(declarantList.get(i).getCompany().getName().equals(declarant.getCompany().getName()))
            {
                restoredDeclarant.setUser(declarantList.get(i).getUser());
                restoredDeclarant.setName(declarantList.get(i).getName());
                restoredDeclarant.setCompany(declarantList.get(i).getCompany());
                break;
            }
        }

        return restoredDeclarant;

    }

//    public Declarant restoreDeclarant(Declarant declarant)
//    {
//        //declarant: user.login, declarant.name, company.name
//        Declarant finalDeclarant = new Declarant();
//
//        DeclarantDao declarantDao = new DeclarantDao();
//        UserDao userDao = new UserDao();
//        CompanyDao companyDao = new CompanyDao();
//
//        User user = userDao.searchByLogin(declarant.getUser().getLogin());
//        Company company = companyDao.searchByName(declarant.getCompany().getName());
//
//        finalDeclarant.setUser(user);
//        finalDeclarant.setCompany(company);
//
//
//
//
//
//        Declarant restoredDeclarant = new Declarant();
//        DeclarantDao declDao = new DeclarantDao();
//        UserDao userDao = new UserDao();
//        CompanyDao companyDao = new CompanyDao();
//        restoredDeclarant.setCompany(companyDao.searchByName(declarant.getCompany().getName()));
//        restoredDeclarant.setUser(userDao.searchByLogin(declarant.getUser().getLogin()));
//        restoredDeclarant.setName(declarant.getName());
//
//        return restoredDeclarant;
//    }
}
