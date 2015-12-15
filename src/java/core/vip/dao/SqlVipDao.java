/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip.dao;

import core.vip.Vip;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author Shay
 */
public class SqlVipDao implements VipDao{

    @Override
    public Vip getVipById(int id) {
         ResultSet vipSet = MySqlUtils.getQuery("SELECT * FROM VIP WHERE id = " + id + ";");

        try {
            vipSet.first();
            Vip order = buildVip(vipSet);
            vipSet.close();
            return order;
        } catch (SQLException ex) {
            Logger.getLogger(SqlVipDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteVipById(int id) {
        MySqlUtils.updateQuery("DELETE FROM VIP WHERE id = " + id );
    }

    @Override
    public void createVip(Vip newVip) {
        String qString = "INSERT INTO VIP "
                + "(id, First_Name, Last_Name, BirthDay, EMail) "
                + "VALUES " + MySqlUtils.valueString(newVip.getId(),
                                                     newVip.getFirstName(),
                                                     newVip.getLastName(),
                                                     //check if birth day is working
                                                     newVip.getBirthDay(),
                                                     newVip.geteMail());

        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void updateVip(Vip newVip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vip buildVip(ResultSet vipSet) {
        try {
            int id = vipSet.getInt("id");
            String firstName = vipSet.getString("First_Name");
            String lastName = vipSet.getString("Last_Name");
            String stringBirthDay = vipSet.getString("BirthDay");
            String eMail = vipSet.getString("EMail");

            DateFormat birthDayDateFormat = new SimpleDateFormat();
            Date birthDay = (Date) birthDayDateFormat.parse(stringBirthDay);

            Vip vip = new Vip(id, firstName, lastName, birthDay, eMail);

            return vip;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(SqlVipDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
