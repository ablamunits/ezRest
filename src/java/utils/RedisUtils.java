/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author borisa
 */
public abstract class RedisUtils {
    public class Tables {
        public static final int TABLE_NUM_OF_GUESTS = 0;
        public static final int TABLE_DESCRIPTION =   1;
        public static final int TABLE_SERVER_ID =     2;
        public static final int TABLE_DISCOUNT =      3;
    }
    
    public class Employee {
        public static final int EMPLOYEE_PERMISSION_ID = 0;
        public static final int EMPLOYEE_FIRST_NAME =    1;
        public static final int EMPLOYEE_LAST_NAME =     2;
        public static final int EMPLOYEE_POSITION =      3;
        public static final int EMPLOYEE_AGE =           4;
        public static final int EMPLOYEE_GENDER =        5;
        public static final int EMPLOYEE_CITY =          6;
        public static final int EMPLOYEE_ADDRESS =       7;
        public static final int EMPLOYEE_EMAIL =         8;
        public static final int EMPLOYEE_PHONE_NUMBER =  9;
        public static final int EMPLOYEE_PASSWORD =     10;
    }
}
