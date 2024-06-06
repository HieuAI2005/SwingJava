/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.ModelStudent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
public class ServiceStudent {
    private static final String connectionSQL = "jdbc:sqlserver://localhost:1433;databaseName=ManagerStudent;user=sa;password=123456789a;encrypt=false";  
    private final ArrayList<ModelStudent> managerStudent; 
    private final Connection conn;
    
    public ServiceStudent(){
        this.managerStudent = new ArrayList<>();
        conn = connectionWithURL();
    }
    
    public static Connection connectionWithURL(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectionSQL);
        }catch(SQLException e){
            Logger.getLogger(ServiceStudent.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Fail connect with Database");
        }
        return conn;
    }
    
    public boolean checkId(String id){
        try(Connection conn = connectionWithURL()){
            boolean check = false;
            String sql = "SELECT * FROM dbo.Student WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                check = true;
            }
            stm.close();
            conn.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    
    public ArrayList<ModelStudent> showInformationTable(){
        try(Connection conn = connectionWithURL()){
            ModelStudent student;
            String search = "SELECT * FROM dbo.Student"; 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(search);
            while(rs.next()){
                String id = rs.getString("id");
                String last_name = rs.getString("last_name");
                String first_name = rs.getString("first_name");
                String birthday = rs.getString("birthday");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String classroom = rs.getString("id_classroom");

                student = new ModelStudent(id,last_name,first_name,birthday,gender,phone,email,classroom);
                this.managerStudent.add(student);
            }
            st.close();
            conn.close();
            return this.managerStudent;
        } catch(SQLException e){
            return new ArrayList<>();
        }
    }
    
    public void addStudent(ModelStudent student){
        try(Connection conn = connectionWithURL()){
            String insert = "INSERT INTO dbo.Student " + "VALUES(?, ?, ?, ?, ?, ? ,? ,?)";
            String selectAll = "SELECT * FROM dbo.Student";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1,student.getId());
                stmt.setString(2,student.getLast_name());
                stmt.setString(3,student.getFirst_name());
                stmt.setString(4,student.getBirthday());
                stmt.setString(5,student.getGender());
                stmt.setString(6,student.getPhone());
                stmt.setString(7,student.getEmail());
                stmt.setString(8,student.getId_classroom());
                stmt.execute();
                conn.prepareStatement(selectAll);
                this.managerStudent.add(student);
            }
            conn.close();
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    
    public ModelStudent searchStudent(String idSearch){
        ModelStudent studentSearch = new ModelStudent();
        for(ModelStudent student : this.managerStudent){
            if(student.getId().equals(idSearch)){
                studentSearch = student;
            }
        }
        return studentSearch;
    } 
    
    public void deleteStudent(String id) throws SQLException{
        String delete = "DELETE FROM dbo.Student WHERE id ='" + id + "'";
        try(Statement st = conn.createStatement()){
            st.executeUpdate(delete);
            st.close();
        }
        conn.close();
        this.managerStudent.removeIf(st -> st.getId().equals(id));
    }
    
    public void updateStudent(ModelStudent student, String id_old){
        try(Connection conn = connectionWithURL()){
            Statement stm = conn.createStatement();
            String update = "UPDATE dbo.Student " +
                    " SET " + 
                    " [id] ='"+ student.getId() + "'" + 
                    ", [last_name] ='" + student.getLast_name() + "'" +      
                    ", [first_name] ='" + student.getFirst_name() + "'" +
                    ", [birthday] ='" + student.getBirthday() + "'" +
                    ", [gender] = '" + student.getGender() + "'" +
                    ", [phone] ='" + student.getPhone() + "'" +
                    ", [email] ='" + student.getEmail() + "'" +
                    ", [id_classroom] ='" + student.getId_classroom() + "'" + 
                    " WHERE id ='" + id_old + "'";
            stm.executeUpdate(update);
            stm.close();
            conn.close();
        }catch(SQLException e){
            System.err.println(e);
        }
    }
}
