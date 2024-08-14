package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.FishSpeciesDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.FishSpecies;
import lk.ijse.fishmarketing.entity.OrderDetail;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;
import lk.ijse.fishmarketing.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishSpeciesDAOImpl implements FishSpeciesDAO {
    @Override
    public boolean save(FishSpecies fishSpecies) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO fishspecies VALUES(?, ?, ?, ?, ?)",fishSpecies.getId(),fishSpecies.getName(),fishSpecies.getQty(),fishSpecies.getMarketPrice(),fishSpecies.getEId());
    }

    @Override
    public FishSpecies searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM fishspecies WHERE fId = ?",id);

        if (resultSet.next()) {
            String fishspecies_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double price = Double.parseDouble(resultSet.getString(4));
            int qty = resultSet.getInt(3);
            String eId = resultSet.getString(5);

            FishSpecies fishSpecies = new FishSpecies(fishspecies_id, name, price, qty, eId);
            return fishSpecies;
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM fishspecies WHERE fId = ?",id);
    }

    @Override
    public boolean update(FishSpecies fishSpecies) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE fishspecies SET name = ?, marketPrice = ?, qty = ? , eId = ? WHERE fId = ?",fishSpecies.getName(),fishSpecies.getMarketPrice(),fishSpecies.getQty(),fishSpecies.getEId(),fishSpecies.getId());
    }

    @Override
    public List<FishSpecies> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM fishspecies");

        List<FishSpecies> fishSpeciesList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double price = Double.parseDouble(resultSet.getString(4));
            int qty = resultSet.getInt(3);
            String eId = resultSet.getString(5);

            FishSpecies fishSpecies = new FishSpecies(id, name, price, qty, eId);
            fishSpeciesList.add(fishSpecies);
        }
        return fishSpeciesList;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> idList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT fId FROM fishspecies");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    @Override
    public boolean updateQty(String fishCode, int qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE fishspecies SET qty = qty - ? WHERE  fId= ?",qty,fishCode);
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT COUNT(*) AS fishspecies_count FROM fishspecies");

        if (resultSet.next()) {
            return resultSet.getInt("fishspecies_count");
        }
        return 0;
    }

}
