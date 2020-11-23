
package jdbc.daos;

import jdbc.models.Course;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class CourseDao {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String HOST = "localhost:3306";
    static final String SCHEMA = "database_design";
    static final String CONFIG = "serverTimezone=UTC";
    static final String DB_URL
            = "jdbc:mysql://"+HOST+"/"+SCHEMA+"?"+CONFIG;

    static final String USER = "dbDesign";
    static final String PASS = "dbDesign";
    static final String UPDATE_COURSE =
            "UPDATE database_design.courses SET title=? WHERE id=?";
    static Connection connection = null;
    static PreparedStatement statement = null;
    Integer status = -1;

    static final String FIND_ALL_COURSES
            = "SELECT * FROM database_design.courses";
    static final String FIND_COURSE_BY_ID =
            "SELECT * FROM courses WHERE id=?";
    static final String CREATE_COURSE
            = "INSERT INTO courses VALUES (?,?)";
    public Integer createCourse(Course course) {
        status = -1;
        connection = getConnection();
        try {
            statement = connection
                    .prepareStatement(CREATE_COURSE);
            statement.setInt(1, course.getId());
            statement.setString(2, course.getTitle());
            status = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public Course findCourseById(Integer courseId) {
        connection = getConnection();
        try {
            statement = connection.prepareStatement(FIND_COURSE_BY_ID);
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String title = resultSet.getString("title");
                Course course = new Course(courseId, title);
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Integer updateCourse(Integer courseId, Course course) {
        connection = getConnection();
        try {
            statement = connection.prepareStatement(UPDATE_COURSE);
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getId());
            status = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }


    public List<Course> findAllCourses() {
        List<Course> courses = new ArrayList<Course>();
        connection = getConnection();
        try {
            statement = connection
                    .prepareStatement(FIND_ALL_COURSES);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                Course course = new Course(id, title);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }
// declare SQL statement
// retrieve all records from courses table
// retrieves course list with records from database
// initialize empty collection of courses
// get live connection from database

// prepare/compile statement

// execute statement as query, return records
// as long as there are records ...
// retrieve ID field value from record
// retrieve TITLE field value from record
// create new course instance with field values
// add course to courses collection

// handle exceptions/errors

// finally, make sure to

// close connection
// close statement


    public static void main(String[] args) {
        CourseDao dao = new CourseDao();
       /* List<Course> courses = dao.findAllCourses();
        for(Course c:courses) {
            System.out.println(c);
        }*/

        Course course = new Course(1, "CS2222");
        Integer code = dao.updateCourse(1, course);
System.out.println(code);
        List<Course> courses = dao.findAllCourses();
        for(Course c:courses) {
            System.out.println(c);
        }

        course = dao.findCourseById(1);
        System.out.println(course);

        course = new Course(346, "CS1234");
        Integer status = dao.createCourse(course);
        System.out.println(status);

        course = dao.findCourseById(345);
        System.out.println(course);
courses = dao.findAllCourses();
for(Course c:courses) {
    System.out.println(c);
}

    }

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,USER,PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    } public static void closeConnection() {
        try {
            if(connection != null) {
                connection.close();
            }
            if(statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

// declare the JDBC driver
// declare the HOST we're connecting to
// declare the SCHEMA name
// declare optional configurations
// build connection string


// declare username
// and password

// connection to connect to database
// statement to execute SQL
// status returned by some SQL command

// gets connection to database

// dynamically load JDBC driver
// login and get connection

// handle exceptions/errors


// return connection if all ok


// import the Course class

// DriverManager represents JDBC library entry point
// Connection interface represents live database connection
// JDBC interface representing executable SQL statement
// Represents records resulting from the database query
// Exception class representing SQL related errors

// Collection classes and interfaces to hold records
// retrieved from the database


// do your work here
