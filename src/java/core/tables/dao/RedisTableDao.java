/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.tables.dao;

import config.RedisConfig;
import core.tables.SingleTableOrder;
import core.tables.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import utils.RedisUtils;

/**
 *
 * @author borisa
 */
public class RedisTableDao implements TableDao {

    private final Jedis redisAccess;

    public RedisTableDao() {
        redisAccess = new Jedis(RedisConfig.CONNECTION);
    }

    @Override
    public List<Table> getAllTables() {
        Set<String> activeTablesSet = redisAccess.smembers("tables");
        ArrayList<Table> activeTables = new ArrayList<>();

        activeTablesSet.stream().forEach((table) -> {
            activeTables.add(getTableById(Integer.parseInt(table)));
        });

        return activeTables;
    }

    @Override
    public Table getTableById(int id) {
        if (redisAccess.exists("tables:" + id)) {
            Table table = new Table();
            List<String> tableInfo = redisAccess.hmget("tables:" + id, "numOfGuests", "description", "serverId", "discount");
            if (tableInfo.isEmpty())
                return null;

            table.setId(id);
            table.setNumOfGuests(Integer.parseInt(tableInfo.get(RedisUtils.Tables.TABLE_NUM_OF_GUESTS)));
            table.setDescription(tableInfo.get(RedisUtils.Tables.TABLE_DESCRIPTION));
            table.setServerId(Integer.parseInt(tableInfo.get(RedisUtils.Tables.TABLE_SERVER_ID)));
            table.setDiscount(Integer.parseInt(tableInfo.get(RedisUtils.Tables.TABLE_DISCOUNT)));

            return table;
        } else {
            System.out.println("Returning null");
            return null;
        }
    }

    @Override
    public void createTable(Table table) {
        HashMap<String, String> map = buildTableMap(table);

        int tableNumber = table.getId();
        String serverId = String.valueOf(table.getServerId());

        if (redisAccess.exists("tables:" + tableNumber) == false) {
            // The table does not exist.. create it:

            // 1. Create the table HASHMAP
            redisAccess.hmset("tables:" + tableNumber, map);

            // 2. Create the table:id:servicedBy SET
            redisAccess.sadd("tables:" + tableNumber + ":servicedBy", serverId);

            // 3. Add the table to a SET of active tables
            redisAccess.sadd("tables", String.valueOf(tableNumber));
        }
    }

    @Override
    public void deleteTableById(int id) {
        redisAccess.del("tables:" + id);
        redisAccess.del("tables:" + id + "order");
        redisAccess.del("tables:" + id + "servicedBy");
        redisAccess.srem("tables", String.valueOf(id));
    }

    @Override
    public void updateTable(int id, Table table) {
        HashMap<String, String> map = new HashMap<>();

        if (table.getDescription() != null) {
            map.put("description", table.getDescription());
        }

        if (table.getNumOfGuests() != 0) {
            map.put("numOfGuests", String.valueOf(table.getNumOfGuests()));
        }

        if (table.getServerId() != 0) {
            map.put("serverId", String.valueOf(table.getServerId()));
        }
        
        if (table.getDiscount() != 0) {
            map.put("discount", String.valueOf(table.getDiscount()));
        }

        if (redisAccess.exists("tables:" + id)) {
            redisAccess.hmset("tables:" + id, map);
        }
    }

    public void updateTableOrder(int tableId, List<SingleTableOrder> tableOrders) {
        if (redisAccess.exists("tables:" + tableId)) {
            for (SingleTableOrder tableOrder : tableOrders) {
                String itemId = String.valueOf(tableOrder.getItemId());
                redisAccess.zincrby("tables:" + tableId + ":order", tableOrder.getQuantity(), itemId);
            }
        }
    }
    
    public List<SingleTableOrder> getTableOrder(int tableId) {
        if (redisAccess.exists("tables:" + tableId)) {
            ArrayList<SingleTableOrder> tableOrders = new ArrayList<>();
            Set<Tuple> rSet = redisAccess.zrangeWithScores("tables:" + tableId + ":order", 0, -1);
            for (Tuple member : rSet) {
                SingleTableOrder order = new SingleTableOrder();
                order.setItemId(Integer.parseInt(member.getElement()));
                order.setQuantity((int) member.getScore());
                
                tableOrders.add(order);
            }
            
            return tableOrders;
        }
        else {
            return null;
        }
    }

    private HashMap<String, String> buildTableMap(Table table) {
        HashMap<String, String> map = new HashMap<>();

        map.put("description", table.getDescription());
        map.put("numOfGuests", String.valueOf(table.getNumOfGuests()));
        map.put("serverId", String.valueOf(table.getServerId()));
        map.put("discount", String.valueOf(table.getDiscount()));

        return map;
    }
}
