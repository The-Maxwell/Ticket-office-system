package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name="SelectAllTicket", query="SELECT t FROM TicketEntity t")
@Entity
@Table(name = "ticket", schema = "ticketoffice")
public class TicketEntity implements IEntity {
    private int ticketNumber;
    private String category;
    private BigDecimal cost;
    private int sequenceNumber;
    private ReceiptEntity receiptByRecieptId;
    private JournaryEntity journaryByJournaryId;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "Ticket_number", nullable = false)
    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Basic
    @Column(name = "Category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "Cost", nullable = false, precision = 2)
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "Sequence_number", nullable = false)
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (ticketNumber != that.ticketNumber) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (sequenceNumber != that.sequenceNumber)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketNumber;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (sequenceNumber);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Reciept_id", referencedColumnName = "Receipt_code", nullable = false)
    public ReceiptEntity getReceiptByRecieptId() {
        return receiptByRecieptId;
    }

    public void setReceiptByRecieptId(ReceiptEntity receiptByRecieptId) {
        this.receiptByRecieptId = receiptByRecieptId;
    }

    @ManyToOne
    @JoinColumn(name = "Journary_id", referencedColumnName = "Journary_number", nullable = false)
    public JournaryEntity getJournaryByJournaryId() {
        return journaryByJournaryId;
    }

    public void setJournaryByJournaryId(JournaryEntity journaryByJournaryId) {
        this.journaryByJournaryId = journaryByJournaryId;
    }

    @Override
    public List<String> recieveEntityInfo() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(ticketNumber));
        row.add(category);
        row.add(String.valueOf(cost));
        row.add(String.valueOf(sequenceNumber));
        row.add(String.valueOf(receiptByRecieptId.getReceiptCode()));
        row.add(String.valueOf(journaryByJournaryId.getJournaryNumber()));
        return row;
    }
    public TicketEntity(){}

    public TicketEntity(String category, String cost, String sequenceNumber, ReceiptEntity receiptByRecieptId, JournaryEntity journaryByJournaryId) throws Exception {
        this();
        if (!category.equals("econom") && !category.equals("medium") && !category.equals("luxe")) throw new Exception("vehicleType can take only following values: econom, medium, luxe");
        this.category = category;
        this.cost = BigDecimal.valueOf(Double.parseDouble(cost));;
        this.sequenceNumber = Integer.parseInt(sequenceNumber);
        if(receiptByRecieptId==null) throw new Exception("Invalid Reciept_id!");
        this.receiptByRecieptId = receiptByRecieptId;
        if(journaryByJournaryId==null) throw new Exception("Invalid Journary_id!");
        this.journaryByJournaryId = journaryByJournaryId;
    }

    public TicketEntity(String ticketNumber, String category, String cost, String sequenceNumber, ReceiptEntity receiptByRecieptId, JournaryEntity journaryByJournaryId) throws Exception {
        this.ticketNumber = Integer.parseInt(ticketNumber);
        if (!category.equals("econom") && !category.equals("medium") && !category.equals("luxe")) throw new Exception("vehicleType can take only following values: econom, medium, luxe");
        this.category = category;
        this.cost = BigDecimal.valueOf(Double.parseDouble(cost));;
        this.sequenceNumber = Integer.parseInt(sequenceNumber);
        if(receiptByRecieptId==null) throw new Exception("Invalid Reciept_id!");
        this.receiptByRecieptId = receiptByRecieptId;
        if(journaryByJournaryId==null) throw new Exception("Invalid Journary_id!");
        this.journaryByJournaryId = journaryByJournaryId;
    }

    @Override
    public String[] recieveColumnsName() {
        return new String[]{"Номер квитка","Категорія","Вартість","Порядковий номер","Код чека","Номер рейсу"};
    }

    @Override
    public String recieveStringInfo() {
        return String.valueOf(ticketNumber)+","+category+","+String.valueOf(cost)+","+String.valueOf(sequenceNumber)+","
                +String.valueOf(receiptByRecieptId.getReceiptCode())+","+String.valueOf(journaryByJournaryId.getJournaryNumber());
    }
}
