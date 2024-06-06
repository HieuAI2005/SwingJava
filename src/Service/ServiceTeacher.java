/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.ModelTeacher;

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
public class ServiceTeacher {
    private static final String connectionSQL = "jdbc:sqlserver://localhost:1433;databaseName=ManagerStudent;user=sa;password=123456789a;encrypt=false";  
    private final ArrayList<ModelTeacher> managerTeacher;
    private final Connection conn;
    
    public ServiceTeacher(){
        this.managerTeacher = new ArrayList<>();
        conn = connectionWithURL();
    }
    
    public static Connection connectionWithURL(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectionSQL);
        }catch(SQLException e){
            Logger.getLogger(ServiceTeacher.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Fail connect with Database");
        }
        return conn;
    }
    
    public boolean checkId(String id){
        try(Connection conn = connectionWithURL()){
            boolean check = false;
            String sql = "SELECT * FROM dbo.Teacher WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1,id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                check = true;
            }
            stm.close();
            conn.close();
            return check;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<ModelTeacher> showInformation(){
        try(Connection conn = connectionWithURL()){
            ModelTeacher teacher;
            String search = "SELECT * FROM dbo.Teacher";
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
                
                teacher = new ModelTeacher(id,last_name,first_name,birthday,gender,phone,email,classroom);
                this.managerTeacher.add(teacher);
            }
            st.close();
            conn.close();
            return this.managerTeacher;
        }catch(SQLException e){
            return new ArrayList<>();
        }
    }
    
    public void addTeacher(ModelTeacher teacher){
        try(Connection conn = connectionWithURL()){
            String insert = "INSERT INTO dbo.Teacher " + "VALUES(?, ?, ?, ?, ?, ? ,? ,?)";
            String selectAll = "SELECT * FROM dbo.Teacher";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1,teacher.getId());
                stmt.setString(2,teacher.getLast_name());
                stmt.setString(3,teacher.getFirst_name());
                stmt.setString(4,teacher.getBirthday());
                stmt.setString(5,teacher.getGender());
                stmt.setString(6,teacher.getPhone());
                stmt.setString(7,teacher.getEmail());
                stmt.setString(8,teacher.getId_classroom());
                stmt.execute();
                conn.prepareStatement(selectAll);
                this.managerTeacher.add(teacher);
            }
            conn.close();
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    
    public void deleteTeacher(String id){
        String delete = "DELETE FROM dbo.Teacher " + "WHERE id ='" + id + "'";
        try(Connection conn = connectionWithURL()){
            Statement st = conn.createStatement();
            st.executeUpdate(delete);
            st.close();
            conn.close();
            this.managerTeacher.removeIf(te -> te.getId().equals(id));
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    
    public void updateTeacher(ModelTeacher teacher, String id_old){
        try(Connection conn = connectionWithURL()){
            Statement st = conn.createStatement();
            String update = "UPDATE dbo.Teacher " +
                    " SET " + 
                    " [id] ='"+ teacher.getId() + "'" + 
                    ", [last_name] ='" + teacher.getLast_name() + "'" +      
                    ", [first_name] ='" + teacher.getFirst_name() + "'" +
                    ", [birthday] ='" + teacher.getBirthday() + "'" +
                    ", [gender] = '" + teacher.getGender() + "'" +
                    ", [phone] ='" + teacher.getPhone() + "'" +
                    ", [email] ='" + teacher.getEmail() + "'" +
                    ", [id_classroom] ='" + teacher.getId_classroom() + "'" + 
                    " WHERE id ='" + id_old + "'";
            st.executeUpdate(update);
            st.close();
            conn.close();
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    
    public ModelTeacher searchTeacher(String idSearch){
        ModelTeacher teacherSearch = new ModelTeacher();
        for(ModelTeacher teacher : this.managerTeacher){
            if(teacher.getId().equals(idSearch)){
                teacherSearch = teacher;
            }
        }
        return teacherSearch;
    }
}
