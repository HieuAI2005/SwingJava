/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class ModelTeacher extends ModelPeople{
    private String id_classroom; 
    
    public ModelTeacher(){
        
    }
    
    public ModelTeacher(String id, String last_name, String first_name, String birthday, String gender, String phone, String email, String id_classroom){
        super(id,last_name,first_name,birthday,gender,phone,email);
        this.id_classroom = id_classroom;
    }

    public String getId_classroom() {
        return id_classroom;
    }

    public void setId_classroom(String id_classroom) {
        this.id_classroom = id_classroom;
    }
    
    @Override
    public String toString(){
        return  "Teacher " + " - " + 
                "ID: " + this.getId() + " - " + 
                "Last_name: " + this.getLast_name() + " - " +
                "First_name: " + this.getFirst_name() + " - " +
                "Birthday: " + this.getBirthday() + " - " + 
                "Gender: " + this.getGender() + " - " + 
                "Phone: " + this.getPhone() + " - " + 
                "Email: " + this.getEmail() + " - " + 
                "Classroom: " + this.getId_classroom() + "\n";
    }
}
