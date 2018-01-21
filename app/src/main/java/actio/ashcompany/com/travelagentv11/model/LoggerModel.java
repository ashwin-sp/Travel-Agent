package actio.ashcompany.com.travelagentv11.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

@Entity(tableName = "LOGGER")
public class LoggerModel {
    private String name;
    private int age;
    private String gender,address, username, password;
    @PrimaryKey
    private int reg;
    private int phno;


    public LoggerModel(String name, int age, String gender, String address, String username, String password, int reg, int phno) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.password = password;
        this.reg = reg;
        this.phno = phno;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getReg() {
        return reg;
    }

    public void setReg(int reg) {
        this.reg = reg;
    }

    public int getPhno() {
        return phno;
    }

    public void setPhno(int phno) {
        this.phno = phno;
    }
}
