package Service;

import DAO.ToyDAO;
import Model.Toy;

import java.util.List;

public class ToyService {

    ToyDAO toyDAO;

    public ToyService(){
        this.toyDAO = new ToyDAO();
    }
    public List<Toy> getAvailableToys(){
        return toyDAO.getAvailableToys();
    }

}
