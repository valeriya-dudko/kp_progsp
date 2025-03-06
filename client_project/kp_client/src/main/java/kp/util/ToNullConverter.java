package kp.util;

public class ToNullConverter {
    public static String emptyStringToNull(String s)
    {
        if(s.equals(""))
        {
            return null;
        }
        return s;
    }
}
