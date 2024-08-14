package lk.ijse.fishmarketing.bo.custom;

import lk.ijse.fishmarketing.bo.SuperBo;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;

import java.sql.SQLException;
import java.util.List;

public interface FishSpeciesBo extends SuperBo {
    List<FishSpeciesDTO> getAllSpecies() throws SQLException, ClassNotFoundException;

    boolean deleteSpecies(String id) throws SQLException, ClassNotFoundException;

    boolean saveSpecies(FishSpeciesDTO fishSpeciesDTO) throws SQLException, ClassNotFoundException;

    boolean updateSpecies(FishSpeciesDTO fishSpeciesDTO) throws SQLException, ClassNotFoundException;

    FishSpeciesDTO searchBySpeciesId(String id) throws SQLException, ClassNotFoundException;

    List<String> getEmployeeIds() throws SQLException, ClassNotFoundException;

    int getSpeciesCount() throws SQLException, ClassNotFoundException;
}
