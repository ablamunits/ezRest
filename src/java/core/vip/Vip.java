/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip;

import java.sql.Date;

/**
 *
 * @author Shay
 */
//POJO
public class Vip {
    int id;
    String firstName;
    String lastName;
    Date birthDay;
    String eMail;

    public Vip(int id, String firstName, String lastName, Date birthDay, String eMail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.eMail = eMail;
    }

    
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String geteMail() {
        return eMail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    
}
