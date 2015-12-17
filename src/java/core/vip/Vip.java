/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import utils.JsonDateAdapter;

/**
 *
 * @author Shay
 */
public class Vip {
    int id;
    String firstName;
    String lastName;
    Date birthday;
    String email;

    public Vip(){}
 
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @XmlJavaTypeAdapter(JsonDateAdapter.class)
    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
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

    public void setBirthday(Date birthDay) {
        this.birthday = birthDay;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }
    
    
}
