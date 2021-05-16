package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NamedQuery(name="SelectAllPassenger", query="SELECT p FROM PassengerEntity p")
@Entity
@Table(name = "passenger", schema = "ticketoffice")
public class PassengerEntity implements IEntity {
    private int passengerCode;
    private String lastName;
    private String firstName;
    private String surname;
    private String category;
    private Collection<ReceiptEntity> receiptsByPassengerCode;

    @Id
    @Column(name = "Passenger_code", nullable = false)
    public int getPassengerCode() {
        return passengerCode;
    }

    public void setPassengerCode(int passengerCode) {
        this.passengerCode = passengerCode;
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
    @Column(name = "Category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassengerEntity that = (PassengerEntity) o;

        if (passengerCode != that.passengerCode)
            return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = passengerCode;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "passengerByPassengerId")
    public Collection<ReceiptEntity> getReceiptsByPassengerCode() {
        return receiptsByPassengerCode;
    }

    public void setReceiptsByPassengerCode(Collection<ReceiptEntity> receiptsByPassengerCode) {
        this.receiptsByPassengerCode = receiptsByPassengerCode;
    }

    @Override
    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(passengerCode));
        row.add(lastName);
        row.add(firstName);
        row.add(surname);
        row.add(category);
        return row;
    }
    public PassengerEntity(){}

    public PassengerEntity(String passengerCode, String lastName, String firstName, String surname, String category) throws Exception {
        this.passengerCode = Integer.parseInt(passengerCode);
        this.lastName = lastName;
        this.firstName = firstName;
        this.surname = surname;
        if (!category.equals("Дитина до 4 років") && !category.equals("Школяр") && !category.equals("Студент")&& !category.equals("Без пільг")
                && !category.equals("Пенсіонер") && !category.equals("Людина з інвалідністю")) throw new Exception("vehicleType can take only following values: Дитина до 4 років, \nШколяр, Студент, Без пільг, Пенсіонер, Людина з інвалідністю");
        this.category = category;
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"Код пасажира","Прізвище","Ім'я","По батькові","Категорія"};
    }

    @Override
    public String recieveStringInfo() {
        return String.valueOf(passengerCode)+","+lastName+","+firstName+","+surname+","+category;
    }
}
