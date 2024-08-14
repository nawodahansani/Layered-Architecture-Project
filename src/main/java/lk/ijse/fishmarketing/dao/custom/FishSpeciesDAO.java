package lk.ijse.fishmarketing.dao.custom;

import lk.ijse.fishmarketing.dao.CrudDAO;
import lk.ijse.fishmarketing.entity.FishSpecies;

import java.sql.SQLException;

public interface FishSpeciesDAO extends CrudDAO<FishSpecies> {
    public boolean updateQty(String fishCode, int qty) throws SQLException, ClassNotFoundException;

}
