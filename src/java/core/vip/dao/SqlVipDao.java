/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip.dao;

import core.vip.Vip;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

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
    public void createVip(Vip vip) {
        String[] columnNames = {
            "Id",
            "First_Name",
            "Last_Name",
            "Birthday",
            "Email"
        };
                        
        Object[] values = {
            vip.getId(),
            vip.getFirstName(),
            vip.getLastName(),
            vip.getBirthday().toString(),
            vip.getEmail()
        };
        
        String qString = new StringBuilder("INSERT INTO VIP ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();
        
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
            String email = vipSet.getString("email");
            Date birthday = vipSet.getDate("Birthday");

            Vip vip = new Vip();
            vip.setId(id);
            vip.setFirstName(firstName);
            vip.setLastName(lastName);
            vip.setBirthday(birthday);
            vip.setEmail(email);

            return vip;
        } catch (SQLException ex) {
            Logger.getLogger(SqlVipDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
