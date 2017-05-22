package Models;

/**
 * Created by this pc on 22-05-17.
 */

public class Student {

    String name;
    String class_sec;
    String address;
    String father_name;
    String mother_name;
    String father_contact;
    String mother_contact;
    String image;

    public Student(String name, String class_sec, String address, String father_name, String mother_name, String father_contact, String mother_contact, String image) {
        this.name = name;
        this.class_sec = class_sec;
        this.address = address;
        this.father_name = father_name;
        this.mother_name = mother_name;
        this.father_contact = father_contact;
        this.mother_contact = mother_contact;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_sec() {
        return class_sec;
    }

    public void setClass_sec(String class_sec) {
        this.class_sec = class_sec;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getFather_contact() {
        return father_contact;
    }

    public void setFather_contact(String father_contact) {
        this.father_contact = father_contact;
    }

    public String getMother_contact() {
        return mother_contact;
    }

    public void setMother_contact(String mother_contact) {
        this.mother_contact = mother_contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
