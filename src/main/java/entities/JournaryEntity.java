package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQuery(name="SelectAllJournary", query="SELECT j FROM JournaryEntity j")
@Entity
@Table(name = "journary", schema = "ticketoffice")
public class JournaryEntity implements IEntity{
    private int journaryNumber;
    private String departurePoint;
    private String destination;
    private Timestamp dateAndTimeOfArrival;
    private Timestamp dateAndTimeOfDeparture;
    private VehicleEntity vehicleByVechileId;
    private int income;
    private Collection<TicketEntity> ticketsByJournaryNumber;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "Journary_number", nullable = false)
    public int getJournaryNumber() {
        return journaryNumber;
    }

    public void setJournaryNumber(int journaryNumber) {
        this.journaryNumber = journaryNumber;
    }

    @Basic
    @Column(name = "Departure_point", nullable = false, length = 80)
    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    @Basic
    @Column(name = "Destination", nullable = false, length = 80)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "Date_and_time_of_arrival", nullable = false)
    public Timestamp getDateAndTimeOfArrival() {
        return dateAndTimeOfArrival;
    }

    public void setDateAndTimeOfArrival(Timestamp dateAndTimeOfArrival) {
        this.dateAndTimeOfArrival = dateAndTimeOfArrival;
    }

    @Basic
    @Column(name = "Date_and_time_of_departure", nullable = false)
    public Timestamp getDateAndTimeOfDeparture() {
        return dateAndTimeOfDeparture;
    }

    public void setDateAndTimeOfDeparture(Timestamp dateAndTimeOfDeparture) {
        this.dateAndTimeOfDeparture = dateAndTimeOfDeparture;
    }

    @Basic
    @Column(name = "Income", nullable = false)
    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        JournaryEntity that = (JournaryEntity) o;
//
//        if (journaryNumber != that.journaryNumber)
//            return false;
//        if (departurePoint != null ? !departurePoint.equals(that.departurePoint) : that.departurePoint != null)
//            return false;
//        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
//        if (dateAndTimeOfArrival != null ? !dateAndTimeOfArrival.equals(that.dateAndTimeOfArrival) : that.dateAndTimeOfArrival != null)
//            return false;
//        if (dateAndTimeOfDeparture != null ? !dateAndTimeOfDeparture.equals(that.dateAndTimeOfDeparture) : that.dateAndTimeOfDeparture != null)
//            return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = journaryNumber;
//        result = 31 * result + (departurePoint != null ? departurePoint.hashCode() : 0);
//        result = 31 * result + (destination != null ? destination.hashCode() : 0);
//        result = 31 * result + (dateAndTimeOfArrival != null ? dateAndTimeOfArrival.hashCode() : 0);
//        result = 31 * result + (dateAndTimeOfDeparture != null ? dateAndTimeOfDeparture.hashCode() : 0);
//        return result;
//    }
//@Override
//public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//
//    JournaryEntity that = (JournaryEntity) o;
//
//    if (journaryNumber != that.journaryNumber)
//        return false;
//    if (departurePoint != null ? !departurePoint.equals(that.departurePoint) : that.departurePoint != null)
//        return false;
//    if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
//    if (dateAndTimeOfArrival != null ? !dateAndTimeOfArrival.equals(that.dateAndTimeOfArrival) : that.dateAndTimeOfArrival != null)
//        return false;
//    if (dateAndTimeOfDeparture != null ? !dateAndTimeOfDeparture.equals(that.dateAndTimeOfDeparture) : that.dateAndTimeOfDeparture != null)
//        return false;
//    if (income != that.income)
//        return false;
//    return true;
//}

//    @Override
//    public int hashCode() {
//        int result = journaryNumber;
//        result = 31 * result + (departurePoint != null ? departurePoint.hashCode() : 0);
//        result = 31 * result + (destination != null ? destination.hashCode() : 0);
//        result = 31 * result + (dateAndTimeOfArrival != null ? dateAndTimeOfArrival.hashCode() : 0);
//        result = 31 * result + (dateAndTimeOfDeparture != null ? dateAndTimeOfDeparture.hashCode() : 0);
//        return result;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JournaryEntity that = (JournaryEntity) o;

        if (journaryNumber != that.journaryNumber) return false;
        if (income != that.income) return false;
        if (departurePoint != null ? !departurePoint.equals(that.departurePoint) : that.departurePoint != null)
            return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        if (dateAndTimeOfArrival != null ? !dateAndTimeOfArrival.equals(that.dateAndTimeOfArrival) : that.dateAndTimeOfArrival != null)
            return false;
        if (dateAndTimeOfDeparture != null ? !dateAndTimeOfDeparture.equals(that.dateAndTimeOfDeparture) : that.dateAndTimeOfDeparture != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = journaryNumber;
        result = 31 * result + (departurePoint != null ? departurePoint.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (dateAndTimeOfArrival != null ? dateAndTimeOfArrival.hashCode() : 0);
        result = 31 * result + (dateAndTimeOfDeparture != null ? dateAndTimeOfDeparture.hashCode() : 0);
        result = 31 * result + income;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Vechile_id", referencedColumnName = "Vehicle_code", nullable = false)
    public VehicleEntity getVehicleByVechileId() {
        return vehicleByVechileId;
    }

    public void setVehicleByVechileId(VehicleEntity vehicleByVechileId) {
        this.vehicleByVechileId = vehicleByVechileId;
    }

    @OneToMany(mappedBy = "journaryByJournaryId")
    public Collection<TicketEntity> getTicketsByJournaryNumber() {
        return ticketsByJournaryNumber;
    }

    public void setTicketsByJournaryNumber(Collection<TicketEntity> ticketsByJournaryNumber) {
        this.ticketsByJournaryNumber = ticketsByJournaryNumber;
    }

    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(journaryNumber));
        row.add(departurePoint);
        row.add(destination);
        row.add(String.valueOf(dateAndTimeOfArrival));
        row.add(String.valueOf(dateAndTimeOfDeparture));
        row.add(String.valueOf(vehicleByVechileId.getVehicleCode()));
        row.add(String.valueOf(income));
        return row;
    }

    public JournaryEntity(){}

    public JournaryEntity( String departurePoint, String destination, String dateAndTimeOfArrival, String dateAndTimeOfDeparture, VehicleEntity vehicleByVechileId) throws Exception {
        this();
        this.departurePoint = departurePoint;
        this.destination = destination;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        this.dateAndTimeOfArrival = new Timestamp(dateFormat.parse(dateAndTimeOfArrival).getTime());
        this.dateAndTimeOfDeparture = new Timestamp(dateFormat.parse(dateAndTimeOfDeparture).getTime());;
        if(vehicleByVechileId==null) throw new Exception("Invalid Vechile_id!");
        this.vehicleByVechileId = vehicleByVechileId;
    }

    public JournaryEntity(String journaryNumber, String departurePoint, String destination, String dateAndTimeOfArrival, String dateAndTimeOfDeparture, VehicleEntity vehicleByVechileId, String income) throws Exception {
        this.journaryNumber = Integer.parseInt(journaryNumber);
        this.departurePoint = departurePoint;
        this.destination = destination;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        this.dateAndTimeOfArrival = new Timestamp(dateFormat.parse(dateAndTimeOfArrival).getTime());
        this.dateAndTimeOfDeparture = new Timestamp(dateFormat.parse(dateAndTimeOfDeparture).getTime());;
        if(vehicleByVechileId==null) throw new Exception("Invalid Vechile_id!");
        this.vehicleByVechileId = vehicleByVechileId;
        this.income = Integer.parseInt(income);
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"Номер рейсу","Місце відправки","Місце прибуття","Дата і час відправки","Дата і час прибуття","Номер транспортного засобу", "Дохід"};
    }
    @Override
    public String recieveStringInfo() {
        return String.valueOf(journaryNumber)+","+departurePoint+","+destination+","+String.valueOf(dateAndTimeOfArrival)+","+String.valueOf(dateAndTimeOfDeparture)
                +","+String.valueOf(vehicleByVechileId.getVehicleCode())+ "," +String.valueOf(income);
    }
}
