package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NamedQuery(name="SelectAllReceipt", query="SELECT r FROM ReceiptEntity r")
@Entity
@Table(name = "receipt", schema = "ticketoffice")
public class ReceiptEntity implements IEntity{
    private int receiptCode;
    private Timestamp dataAndTimeOfSale;
    private Timestamp dataAndTimeOfBooking;
    private BigDecimal totalPrice;
    private PassengerEntity passengerByPassengerId;
    private UserEntity userByUserId;
    private Collection<TicketEntity> ticketsByReceiptCode;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "Receipt_code", nullable = false)
    public int getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(int receiptCode) {
        this.receiptCode = receiptCode;
    }

    @Basic
    @Column(name = "Data_and_time_of_sale", nullable = false)
    public Timestamp getDataAndTimeOfSale() {
        return dataAndTimeOfSale;
    }

    public void setDataAndTimeOfSale(Timestamp dataAndTimeOfSale) {
        this.dataAndTimeOfSale = dataAndTimeOfSale;
    }

    @Basic
    @Column(name = "Data_and_time_of_booking", nullable = true)
    public Timestamp getDataAndTimeOfBooking() {
        return dataAndTimeOfBooking;
    }

    public void setDataAndTimeOfBooking(Timestamp dataAndTimeOfBooking) {
        this.dataAndTimeOfBooking = dataAndTimeOfBooking;
    }

    @Basic
    @Column(name = "Total_price", nullable = false, precision = 2)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptEntity that = (ReceiptEntity) o;

        if (receiptCode != that.receiptCode) return false;
        if (dataAndTimeOfSale != null ? !dataAndTimeOfSale.equals(that.dataAndTimeOfSale) : that.dataAndTimeOfSale != null)
            return false;
        if (dataAndTimeOfBooking != null ? !dataAndTimeOfBooking.equals(that.dataAndTimeOfBooking) : that.dataAndTimeOfBooking != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = receiptCode;
        result = 31 * result + (dataAndTimeOfSale != null ? dataAndTimeOfSale.hashCode() : 0);
        result = 31 * result + (dataAndTimeOfBooking != null ? dataAndTimeOfBooking.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Passenger_id", referencedColumnName = "Passenger_code", nullable = false)
    public PassengerEntity getPassengerByPassengerId() {
        return passengerByPassengerId;
    }

    public void setPassengerByPassengerId(PassengerEntity passengerByPassengerId) {
        this.passengerByPassengerId = passengerByPassengerId;
    }

    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "ID", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @OneToMany(mappedBy = "receiptByRecieptId")
    public Collection<TicketEntity> getTicketsByReceiptCode() {
        return ticketsByReceiptCode;
    }

    public void setTicketsByReceiptCode(Collection<TicketEntity> ticketsByReceiptCode) {
        this.ticketsByReceiptCode = ticketsByReceiptCode;
    }

    @Override
    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(receiptCode));
        row.add(String.valueOf(dataAndTimeOfSale));
        row.add(String.valueOf(dataAndTimeOfBooking));
        row.add(String.valueOf(totalPrice));
        row.add(String.valueOf(passengerByPassengerId.getPassengerCode()));
        row.add(String.valueOf(userByUserId.getId()));
        return row;
    }
    public ReceiptEntity(){}

    public ReceiptEntity( String dataAndTimeOfSale, String dataAndTimeOfBooking, String totalPrice, PassengerEntity passengerByPassengerId, UserEntity userByUserId) throws Exception {
        this();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        this.dataAndTimeOfSale = new Timestamp(dateFormat.parse(dataAndTimeOfSale).getTime());
        this.dataAndTimeOfBooking = new Timestamp(dateFormat.parse(dataAndTimeOfBooking).getTime());
        this.totalPrice = BigDecimal.valueOf(Double.parseDouble(totalPrice));
        if(passengerByPassengerId==null) throw new Exception("Invalid Passenger_id!");
        this.passengerByPassengerId = passengerByPassengerId;
        this.userByUserId = userByUserId;
    }

    public ReceiptEntity(String receiptCode, String dataAndTimeOfSale, String dataAndTimeOfBooking, String totalPrice, PassengerEntity passengerByPassengerId, UserEntity userByUserId) throws Exception {
        this.receiptCode = Integer.parseInt(receiptCode);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        this.dataAndTimeOfSale = new Timestamp(dateFormat.parse(dataAndTimeOfSale).getTime());
        this.dataAndTimeOfBooking = new Timestamp(dateFormat.parse(dataAndTimeOfBooking).getTime());
        this.totalPrice = BigDecimal.valueOf(Double.parseDouble(totalPrice));
        if(passengerByPassengerId==null) throw new Exception("Invalid Passenger_id!");
        this.passengerByPassengerId = passengerByPassengerId;
        this.userByUserId = userByUserId;
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"Код чека","Дата і час продажі","Дата і час бронювання","Загальна ціна","Код пасажира", "Код працівника"};
    }
}
