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
public class StringUtils {
    public static String arrayToString(String[] arr) {
        StringBuilder sb = new StringBuilder();
        int lastIndex = arr.length - 1;
        
        for (int index = 0; index < lastIndex; index++) {
            sb.append(arr[index]);
            sb.append(", ");
        }
        
        sb.append(arr[lastIndex]);
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public static String objectsArrayToString(Object[] arr) {
        StringBuilder sb = new StringBuilder();
        int lastIndex = arr.length - 1;
        
        for (int index = 0; index <= lastIndex; index++) {
            
            if (arr[index] instanceof String) {
                sb.append("\"");
                sb.append(arr[index].toString());
                sb.append("\"");
            } 
            else if (arr[index] == null) {
                sb.append("NULL");
            }
            else {
                sb.append(arr[index].toString());
            }
            
            if(index != lastIndex)
                sb.append(", ");
        }
        
        return sb.toString();
    }
}
