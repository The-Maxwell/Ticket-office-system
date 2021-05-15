package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQuery(name="SelectAllVehicle", query="SELECT v FROM VehicleEntity v")
@Entity
@Table(name = "vehicle", schema = "ticketoffice")
public class VehicleEntity implements IEntity {
    private int vehicleCode;
    private String vehicleType;
    private int numberOfSeats;
    private int numberOfEconomyClassSeats;
    private int numberOfMediumClassSeats;
    private int numberOfLuxuryClassSeats;
    private String vechileCompany;
    private Collection<JournaryEntity> journariesByVehicleCode;

    @Id
    @Column(name = "Vehicle_code", nullable = false)
    public int getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(int vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    @Basic
    @Column(name = "Vehicle_type", nullable = false)
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Basic
    @Column(name = "Number_of_seats", nullable = false)
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Basic
    @Column(name = "Number_of_economy_class_seats", nullable = false)
    public int getNumberOfEconomyClassSeats() {
        return numberOfEconomyClassSeats;
    }

    public void setNumberOfEconomyClassSeats(int numberOfEconomyClassSeats) {
        this.numberOfEconomyClassSeats = numberOfEconomyClassSeats;
    }

    @Basic
    @Column(name = "Number_of_medium_class_seats", nullable = false)
    public int getNumberOfMediumClassSeats() {
        return numberOfMediumClassSeats;
    }

    public void setNumberOfMediumClassSeats(int numberOfMediumClassSeats) {
        this.numberOfMediumClassSeats = numberOfMediumClassSeats;
    }

    @Basic
    @Column(name = "Number_of_luxury_class_seats", nullable = false)
    public int getNumberOfLuxuryClassSeats() {
        return numberOfLuxuryClassSeats;
    }

    public void setNumberOfLuxuryClassSeats(int numberOfLuxuryClassSeats) {
        this.numberOfLuxuryClassSeats = numberOfLuxuryClassSeats;
    }

    @Basic
    @Column(name = "Vechile_company", nullable = true, length = 80)
    public String getVechileCompany() {
        return vechileCompany;
    }

    public void setVechileCompany(String vechileCompany) {
        this.vechileCompany = vechileCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleEntity that = (VehicleEntity) o;

        if (vehicleCode != that.vehicleCode) return false;
        if (vehicleType != null ? !vehicleType.equals(that.vehicleType) : that.vehicleType != null) return false;
        if (numberOfSeats != that.numberOfSeats)
            return false;
        if (numberOfEconomyClassSeats != that.numberOfEconomyClassSeats)
            return false;
        if (numberOfMediumClassSeats != that.numberOfMediumClassSeats)
            return false;
        if (numberOfLuxuryClassSeats != that.numberOfLuxuryClassSeats)
            return false;
        if (vechileCompany != null ? !vechileCompany.equals(that.vechileCompany) : that.vechileCompany != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleCode, vehicleType, numberOfSeats, numberOfEconomyClassSeats, numberOfMediumClassSeats, numberOfLuxuryClassSeats, vechileCompany);
    }

    @OneToMany(mappedBy = "vehicleByVechileId")
    public Collection<JournaryEntity> getJournariesByVehicleCode() {
        return journariesByVehicleCode;
    }

    public void setJournariesByVehicleCode(Collection<JournaryEntity> journariesByVehicleCode) {
        this.journariesByVehicleCode = journariesByVehicleCode;
    }

    @Override
    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(vehicleCode));
        row.add(vehicleType);
        row.add(String.valueOf(numberOfSeats));
        row.add(String.valueOf(numberOfEconomyClassSeats));
        row.add(String.valueOf(numberOfMediumClassSeats));
        row.add(String.valueOf(numberOfLuxuryClassSeats));
        row.add(vechileCompany);
        return row;
    }
    public VehicleEntity(){}
    public VehicleEntity(String vehicleCode, String vehicleType, String numberOfSeats, String numberOfEconomyClassSeats, String numberOfMediumClassSeats,
                         String numberOfLuxuryClassSeats, String vechileCompany) throws Exception {
        this.vehicleCode = Integer.parseInt(vehicleCode);
        if (!vehicleType.equals("train") && !vehicleType.equals("bus") && !vehicleType.equals("airplane")) throw new Exception("vehicleType can take only following values: train, bus, airplane");
        this.vehicleType = vehicleType;
        this.numberOfSeats = Integer.parseInt(numberOfSeats);
        this.numberOfEconomyClassSeats = Integer.parseInt(numberOfEconomyClassSeats);
        this.numberOfMediumClassSeats = Integer.parseInt(numberOfMediumClassSeats);
        this.numberOfLuxuryClassSeats = Integer.parseInt(numberOfLuxuryClassSeats);
        this.vechileCompany = vechileCompany;
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"Vehicle_code","Vehicle_type","Number_of_seats","Number_of_economy_class_seats","Number_of_medium_class_seats","Number_of_luxury_class_seats","Vechile_company"};
    }

    @Override
    public String recieveStringInfo() {
        return String.valueOf(vehicleCode)+","+vehicleType+","+String.valueOf(numberOfSeats)+","+String.valueOf(numberOfEconomyClassSeats)
                +","+String.valueOf(numberOfMediumClassSeats)+","+String.valueOf(numberOfLuxuryClassSeats)+","+vechileCompany;
    }
}
