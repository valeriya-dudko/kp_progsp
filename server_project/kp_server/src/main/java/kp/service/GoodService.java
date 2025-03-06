package kp.service;

import kp.model.*;
import kp.dao.*;
import kp.util.*;

public class GoodService {
    public static Double calcTax(Good good)
    {
        double tax = 50; //сбор за оформление в электронном виде - 50 руб
        if(good.getRateType() == 1)
        {
            tax = tax + calcAdvalor(good);
        }
        else if(good.getRateType() == 2)
        {
            tax = tax + calcSpecific(good);
        }
        else if(good.getRateType() == 3) {
            tax = calcAdvalor(good) >= calcSpecific(good) ? tax + calcAdvalor(good) : tax + calcSpecific(good);
        }

        if(good.getIsImport())
        {
            tax = tax + (good.getExcise() * good.getAmount());
            tax = tax + (tax * ((double) good.getVAT() /100));

        }
        return tax;
    }

    private static Double calcAdvalor(Good good)
    {
        return good.getPrice() * good.getAmount() * (good.getRate()/100);
    }

    private static Double calcSpecific(Good good)
    {
        return good.getAmount() * good.getRate() * 3.6;
    }
}
