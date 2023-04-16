import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.security.InvalidKeyException;

public class DatabaseManagerTest {
    @Test
    public void testConnect() {
        DatabaseManager mgr = new DatabaseManager();
        
        //create project
        Project project = new Project();
        project.DatabaseName = "TestProject";
        //set user
        User user = new User("Test User", "password");
        App.user = user;

        //set url
        App.DBPath = "jdbc:sqlite:./";

        //connect
        
        try {
            mgr.connect(project);
        } catch (SQLException e) {
            fail("Failed to connect to database");
        }

        //check if connected successfully
        assertTrue(mgr.getConnectedProject() == project);

        //disconnect
        try {
            mgr.disconnect();
        } catch (SQLException e) {
            fail("Failed to disconnect from database");
        }

        //delete database
        File dbFile = new File("effort.db");
        dbFile.delete();

    }

    @Test
    public void testExecuteQueryAndUpdate() throws InvalidKeyException, SQLException {
        DatabaseManager mgr = new DatabaseManager();
        
        //create project
        Project project = new Project();
        project.DatabaseName = "TestProject";
        //set user
        User user = new User("Test User", "password");
        App.user = user;

        //set url
        App.DBPath = "jdbc:sqlite:./";

        //connect
        try {
            mgr.connect(project);
        } catch (SQLException e) {
            fail("Failed to connect to database");
        }

        //check if connected successfully
        assertTrue(mgr.getConnectedProject() == project);

        //add a table
        try {
            mgr.executeUpdate("CREATE TABLE IF NOT EXISTS TestTable (id INT, name VARCHAR(255))");
        } catch (SQLException e) {
            fail("Failed to create table");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //add a row
        try {
            mgr.executeUpdate("INSERT INTO TestTable (id, name) VALUES (?, ?)", 1,"Test Name");
        } catch (SQLException e) {
            fail("Failed to insert row");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //check if row exists
    
        ResultSet rs = mgr.executeQuery("SELECT * FROM TestTable WHERE id = ?", 1);
        assertTrue(rs.next());
        int id = rs.getInt("id");
        String name = rs.getString("name");

        //print values
        System.out.println("id: " + id);
        System.out.println("name: " + name);

        assertTrue(id == 1);
        assertTrue(name.equals("Test Name"));
    

        //disconnect
        try {
            mgr.disconnect();
        } catch (SQLException e) {
            fail("Failed to disconnect from database");
        }

        //delete database
        File dbFile = new File("effort.db");
        dbFile.delete();
    }
}
