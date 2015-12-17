/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author borisa
 * Used to convert Strings from java.util.Date format to SQL format.
 */
public class JsonDateAdapter extends XmlAdapter<String, java.sql.Date> {
    @Override
    public Date unmarshal(String v) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date uDate = sdf.parse(v);
        Date sDate = new Date(uDate.getTime());
        return sDate;
    }

    @Override
    public String marshal(java.sql.Date v) throws Exception {
        return v.toString();
    }
}
