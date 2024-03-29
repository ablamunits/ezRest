/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip.dao;

import core.vip.Vip;
import java.util.List;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface VipDao {
    Vip getVipById(int id);
    List<Vip> getAllVip();
    void deleteVipById(int orderId);
    void createVip(Vip newVip);
    void updateVip(int id, Vip vip);
}
