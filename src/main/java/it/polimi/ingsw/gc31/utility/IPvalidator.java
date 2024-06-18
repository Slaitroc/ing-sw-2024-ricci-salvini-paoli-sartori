package it.polimi.ingsw.gc31.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPvalidator {

    public static boolean isValid(String ip) {
        if (ip == null || ip.isEmpty())
            return true;

        String regPattern = "^\\d{1,3}(\\.\\d{1,3}){3}$";
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();

    }

}
