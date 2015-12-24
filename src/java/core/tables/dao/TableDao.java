/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.tables.dao;

import core.tables.Table;
import java.util.List;

/**
 *
 * @author borisa
 */
public interface TableDao {
    List<Table> getAllTables();
    Table getTableById(int id);
    void createTable(Table table);
    void deleteTableById(int id);
    void updateTable(int id, Table table);
}
