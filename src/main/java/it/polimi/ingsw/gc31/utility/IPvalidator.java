package it.polimi.ingsw.gc31.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IPvalidator class provides the functionality to check if an IP address is valid.
 */
public class IPvalidator {

    /**
     * Check if the ip is valid
     * 
     * @param ip the ip to check
     * @return true if the ip is valid, false otherwise
     */
    public static boolean isValid(String ip) {
        if (ip == null || ip.isEmpty())
            return true;

        String regPattern = "^\\d{1,3}(\\.\\d{1,3}){3}$";
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();

    }

}
