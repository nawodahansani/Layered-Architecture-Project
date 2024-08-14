package lk.ijse.fishmarketing.bo;

import lk.ijse.fishmarketing.bo.custom.impl.*;

import java.lang.invoke.SwitchPoint;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){
    }
    public static BoFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }
    public enum Type{
       Customer,Employee,FishSpecies,Orders,User;
    }
    public SuperBo getBo(Type type){
        switch (type){
            case Customer :
                return new CustomerBoImpl();
            case Employee:
                return new EmployeeBoImpl();
            case FishSpecies:
                return new FishSpeciesBoImpl();
            case Orders:
                return new OrdersBoImpl();
            case User:
                return new UserBoImpl();
            default:
                return null;
        }
    }
}
