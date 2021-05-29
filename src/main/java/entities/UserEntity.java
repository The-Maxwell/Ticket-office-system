package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name="SelectAllUser", query="SELECT u FROM UserEntity u")
@Entity
@Table(name = "user", schema = "ticketoffice_c")
public class UserEntity implements IEntity{
    private int id;
    private String lastName;
    private String firstName;
    private String surname;
    private String email;
    private int age;
    private String role;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Last_name", nullable = false, length = 40)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "First_name", nullable = false, length = 40)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "Surname", nullable = false, length = 40)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "Email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "Role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (age != that.age) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(id));
        row.add(lastName);
        row.add(firstName);
        row.add(surname);
        row.add(email);
        row.add(String.valueOf(age));
        row.add(role);
        return row;
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"ID користувача","Прізвище","Ім'я","По батькові","Email","Вік","Роль"};
    }

    @Override
    public String recieveStringInfo() {
        return String.valueOf(id)+","+lastName+","+firstName+","+surname+","+email+","+age+","+role;
    }
    public UserEntity(){

    }
    public UserEntity(String lastName, String firstName, String surname, String email, String age, String role) throws Exception {
        this.lastName = lastName;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.age = Integer.parseInt(age);;
        this.role = role;
    }
    public UserEntity(String id, String lastName, String firstName, String surname, String email, String age, String role) throws Exception {
        this.id = Integer.parseInt(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.age = Integer.parseInt(age);;
        this.role = role;
    }
}
