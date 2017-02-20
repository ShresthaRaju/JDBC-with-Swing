package edu.lfa.dao;

import edu.lfa.entity.Person;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author RAJU
 */
public interface PersonDao {

    public String insert(Person p) throws ClassNotFoundException, SQLException;

    public Person search(int id) throws ClassNotFoundException, SQLException;

    public String update(Person p, int id) throws ClassNotFoundException, SQLException;

    public String delete(int id) throws ClassNotFoundException, SQLException;

    public List<Person> viewAll() throws ClassNotFoundException, SQLException;

}
