package ma.projet.service;

import ma.projet.dao.IDaoImpl;
import ma.projet.classes.EmployeTache; // Changé de EmployeeTache à EmployeTache
import java.util.List;

public class EmployeTacheService {
    private IDaoImpl<EmployeTache> dao; // Changé de EmployeeTache à EmployeTache

    public EmployeTacheService() {
        this.dao = new IDaoImpl<>(EmployeTache.class); // Changé de EmployeeTache à EmployeTache
    }

    // CRUD methods
    public boolean create(EmployeTache o) { // Changé de EmployeeTache à EmployeTache
        return dao.create(o);
    }

    public boolean update(EmployeTache o) { // Changé de EmployeeTache à EmployeTache
        return dao.update(o);
    }

    public boolean delete(EmployeTache o) { // Changé de EmployeeTache à EmployeTache
        return dao.delete(o);
    }

    public EmployeTache findById(int id) { // Changé de EmployeeTache à EmployeTache
        return dao.findById(id);
    }

    public List<EmployeTache> findAll() { // Changé de EmployeeTache à EmployeTache
        return dao.findAll();
    }
}