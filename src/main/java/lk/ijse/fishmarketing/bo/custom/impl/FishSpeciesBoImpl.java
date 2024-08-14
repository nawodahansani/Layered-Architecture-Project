package lk.ijse.fishmarketing.bo.custom.impl;

import lk.ijse.fishmarketing.bo.custom.FishSpeciesBo;
import lk.ijse.fishmarketing.dao.DAOFactory;
import lk.ijse.fishmarketing.dao.custom.EmployeeDAO;
import lk.ijse.fishmarketing.dao.custom.FishSpeciesDAO;
import lk.ijse.fishmarketing.entity.FishSpecies;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishSpeciesBoImpl implements FishSpeciesBo {
    FishSpeciesDAO fishSpeciesDAO = (FishSpeciesDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.FishSpecies);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.Employee);
    @Override
    public List<FishSpeciesDTO> getAllSpecies() throws SQLException, ClassNotFoundException{
        List<FishSpecies> fishSpeciesList = fishSpeciesDAO.getAll();
        List<FishSpeciesDTO> fishSpeciesDTOS = new ArrayList<>();
        for (FishSpecies fishSpecies : fishSpeciesList){
            FishSpeciesDTO fishSpeciesDTO = new FishSpeciesDTO(fishSpecies.getId(), fishSpecies.getName(), fishSpecies.getQty(),fishSpecies.getMarketPrice(), fishSpecies.getEId());
            fishSpeciesDTOS.add(fishSpeciesDTO);
        }
        return fishSpeciesDTOS;
    }
    @Override
    public boolean deleteSpecies(String id) throws SQLException, ClassNotFoundException{
        return fishSpeciesDAO.delete(id);
    }
    @Override
    public boolean saveSpecies(FishSpeciesDTO fishSpeciesDTO) throws SQLException, ClassNotFoundException{
        return fishSpeciesDAO.save(new FishSpecies(fishSpeciesDTO.getId(), fishSpeciesDTO.getName(), fishSpeciesDTO.getMarketPrice(), fishSpeciesDTO.getQty(), fishSpeciesDTO.getEId()));
    }
    @Override
    public boolean updateSpecies(FishSpeciesDTO fishSpeciesDTO) throws SQLException, ClassNotFoundException{
        return fishSpeciesDAO.update(new FishSpecies(fishSpeciesDTO.getId(),fishSpeciesDTO.getName(), fishSpeciesDTO.getMarketPrice(),fishSpeciesDTO.getQty(),fishSpeciesDTO.getEId()));
    }
    @Override
    public FishSpeciesDTO searchBySpeciesId(String id) throws SQLException, ClassNotFoundException {
        FishSpecies fishSpecies = fishSpeciesDAO.searchById(id);
        return new FishSpeciesDTO(fishSpecies.getId(), fishSpecies.getName(), fishSpecies.getQty(),fishSpecies.getMarketPrice(), fishSpecies.getEId());
    }
    @Override
    public List<String> getEmployeeIds() throws SQLException, ClassNotFoundException{
        return employeeDAO.getIds();
    }
    @Override
    public int getSpeciesCount() throws SQLException, ClassNotFoundException {
        return fishSpeciesDAO.getCount();
    }
}
