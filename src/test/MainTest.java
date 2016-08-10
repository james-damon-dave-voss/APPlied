import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Dan on 6/17/16.
 */
public class MainTest {
    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        user = Main.selectUser(conn, user.username);
        conn.close();
        assertTrue(user != null);
    }

    @Test
    public void testJobs() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        Job job1 = new Job(1, "company", "place", "salary", "contact", "555", "email", true, "comments", user.userId);
        Main.insertJob(conn, job1);
        Job job2 = new Job(2, "iron yard", "chs", "salary", "contact", "555", "email", false, "comments", user.userId);
        Main.insertJob(conn, job2);
        Job job3 = new Job(3, "mcdonalds", "nyc", "salary", "contact", "555", "email", true, "comments", user.userId);
        Main.insertJob(conn, job3);
        ArrayList<Job> jobs = Main.selectJobs(conn, 1);
        conn.close();
        assertTrue(jobs.size() == 3);
    }

    @Test
    public void testUpdate() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        Job job = new Job(1, "company", "place", "salary", "contact", "555", "email", true, "comments", user.userId);
        Main.insertJob(conn, job);
        Job updatedJob = new Job(1, "com", "loc", "salary", "contact", "550", "email", false, "comments", user.userId);
        Main.updateJobs(conn, updatedJob);
        ArrayList<Job> jobs = Main.selectJobs(conn, user.userId);
        conn.close();
        assertTrue(jobs.size() == 1);
        assertTrue(job.userId == updatedJob.userId);
        assertTrue(jobs.get(0).companyName.equals("com"));
    }

    @Test
    public void testDelete() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        Job job = new Job(1, "company", "place", "salary", "contact", "555", "email", true, "comments", user.userId);
        Main.deleteJobs(conn, 1);
        ArrayList<Job> jobs = Main.selectJobs(conn, user.userId);
        conn.close();
        assertTrue(jobs.size() == 0);

    }
}