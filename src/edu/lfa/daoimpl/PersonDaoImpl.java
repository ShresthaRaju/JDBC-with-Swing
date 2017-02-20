package edu.lfa.daoimpl;

import edu.lfa.dao.PersonDao;
import edu.lfa.dbutil.Database;
import edu.lfa.entity.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAJU
 */
public class PersonDaoImpl implements PersonDao {

    private Database database = new Database();
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String sql;

    @Override
    public String insert(Person p) throws ClassNotFoundException, SQLException {

        database.connectToDatabase();
        sql = "insert into tbl_person(full_name,address,contact_number,email)values (?,?,?,?)";
        statement = database.initStatement(sql);
        statement.setString(1, p.getFullName());
        statement.setString(2, p.getAddress());
        statement.setString(3, p.getContactNumber());
        statement.setString(4, p.getEmail());

        database.executeUpdate();

        database.disconnectDatabase();

        return "Successfully Inserted!";

    }

    @Override
    public Person search(int id) throws ClassNotFoundException, SQLException {

        Person person = new Person();
        database.connectToDatabase();
        sql = "select * from tbl_person where person_id=?";
        statement = database.initStatement(sql);
        statement.setInt(1, id);

        resultSet = database.executeQuery();

        int result = 0;

        if (resultSet.next()) {

            person.setFullName(resultSet.getString("full_name"));
            person.setAddress(resultSet.getString("address"));
            person.setContactNumber(resultSet.getString("contact_number"));
            person.setEmail(resultSet.getString("email"));

            result++;
        }
        database.disconnectDatabase();

        if (result == 0) {
            return null;

        } else {

            return person;

        }
    }

    @Override
    public String update(Person p, int id) throws ClassNotFoundException, SQLException {

        database.connectToDatabase();

        sql = "update tbl_person set full_name=?,address=?,contact_number=?,email=? where person_id=?";
        statement = database.initStatement(sql);
        statement.setString(1, p.getFullName());
        statement.setString(2, p.getAddress());
        statement.setString(3, p.getContactNumber());
        statement.setString(4, p.getEmail());
        statement.setInt(5, id);

        database.executeUpdate();

        database.disconnectDatabase();

        return "Successfully Updated!";

    }

    @Override
    public String delete(int id) throws ClassNotFoundException, SQLException {

        database.connectToDatabase();
        sql = "delete from tbl_person where person_id=?";
        statement = database.initStatement(sql);
        statement.setInt(1, id);

        database.executeUpdate();

        database.disconnectDatabase();

        return "Successfully Deleted!";

    }

    @Override
    public List<Person> viewAll() throws ClassNotFoundException, SQLException {

        List<Person> personList = new ArrayList<>();
        database.connectToDatabase();

        sql = "Select * from tbl_person";

        statement = database.initStatement(sql);
        resultSet = database.executeQuery();

        int res = 0;

        while (resultSet.next()) {
            Person person = new Person();
            person.setId(resultSet.getInt("person_id"));
            person.setFullName(resultSet.getString("full_name"));
            person.setAddress(resultSet.getString("address"));
            person.setContactNumber(resultSet.getString("contact_number"));
            person.setEmail(resultSet.getString("email"));

            personList.add(person);
            res++;

        }
        database.disconnectDatabase();
        if (res == 0) {
            return null;

        } else {
            return personList;
        }
    }

}
