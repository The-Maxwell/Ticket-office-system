package utils;

import entities.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TicketOfficeDao {
    public List<IEntity> selectEntities(String itemId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<IEntity> result = null;
        try {
            session.beginTransaction();
            Query query = null;
            switch (itemId) {
                case "journary":
                    query = session.createNamedQuery("SelectAllJournary");
                    break;
                case "vehicle":
                    query = session.createNamedQuery("SelectAllVehicle");
                    break;
                case "receipt":
                    query = session.createNamedQuery("SelectAllReceipt");
                    break;
                case "passenger":
                    query = session.createNamedQuery("SelectAllPassenger");
                    break;
                case "ticket":
                    query = session.createNamedQuery("SelectAllTicket");
                    break;
            }
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public String insertEntity(String entityString, String table) {
        String result = null;
        entityString = entityString.trim();
        if (entityString == null) {
            return "Error. Empty input!";
        }
        int fieldCount = getEntityFieldCount(table);
        String[] arrEntityString = entityString.split(",");

        if (arrEntityString == null || arrEntityString.length != fieldCount) {
            return "Error. Empty input!";
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(getNeededEntity(arrEntityString, session, table));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            result = e.getMessage();
        } finally {
            session.close();
            return result;
        }
    }

    public boolean updateEntity(String entityString, String table) {
        boolean result = false;
        entityString = entityString.trim();
        if (entityString == null) {
            return result;
        }
        int fieldCount = getEntityFieldCount(table);

        String[] arrEntityString = entityString.split(",");
        if (arrEntityString == null || arrEntityString.length != fieldCount) {
            return result;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(getNeededEntity(arrEntityString, session, table));
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            return result;
        }
    }

    public boolean deleteEntity(String ID, String table) {
        boolean result = false;
        int id;
        try {
            id = Integer.parseInt(ID);
        } catch (Exception e) {
            return result;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            IEntity entity = (IEntity) session.load(getEntityClass(table), id);

            session.remove(entity);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
            return result;
        }
    }

    public Class getEntityClass(String table) {
        switch (table) {
            case "vehicle":
                return VehicleEntity.class;
            case "journary":
                return JournaryEntity.class;
            case "receipt":
                return ReceiptEntity.class;
            case "ticket":
                return TicketEntity.class;
            case "passenger":
                return PassengerEntity.class;
            default:
                return null;
        }
    }

    public Object getNeededEntity(String[] arrEntityString, Session session, String table) throws Exception {
        switch (table) {
            case "vehicle":
                return new VehicleEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], arrEntityString[5], arrEntityString[6]);
            case "journary":
                int idVehicle = Integer.parseInt(arrEntityString[5]);
                return new JournaryEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], session.load(VehicleEntity.class, idVehicle));
            case "receipt":
                int idPassenger = Integer.parseInt(arrEntityString[4]);
                return new ReceiptEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], session.load(PassengerEntity.class, idPassenger));
            case "ticket":
                int idReceipt = Integer.parseInt(arrEntityString[4]);
                int idJournary = Integer.parseInt(arrEntityString[5]);
                return new TicketEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], session.load(ReceiptEntity.class, idReceipt), session.load(JournaryEntity.class, idJournary));
            case "passenger":
                return new PassengerEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3]);
            default:
                return null;
        }
    }

    public int getEntityFieldCount(String table) {
        switch (table) {
            case "vehicle":
                return 7;
            case "ticket":
            case "journary":
                return 6;
            case "receipt":
            case "passenger":
                return 5;
            default:
                return 0;
        }
    }

    public List<IEntity> searchBySpecificParams(String table, String ...param) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List result = null;
        try {
            session.beginTransaction();

            Criteria criteria = getNeededCriteriaForSearch(session, table, param);
            result = criteria.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    private Criteria getNeededCriteriaForSearch(Session session, String table, String ...param){
        switch (table) {
            case "vehicle":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("vehicleType", param[0]));
            case "journary":
                try {
                    Criteria criteria = session.createCriteria(getEntityClass(table)).add(Restrictions.like("departurePoint", "%"+param[0]+"%"));
                    if(!param[1].equals(""))
                        criteria.add(Restrictions.eq("dateAndTimeOfArrival", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S").parse(param[1])));
                    return criteria;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            case "receipt":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("passengerByPassengerId", searchEntity(param[0],table)));
            case "ticket":
            case "passenger":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("category", param[0]));
        }
        return null;
    }

    public IEntity searchEntity(String ID, String table) {
        IEntity entity = null;
        int id;
        try {
            id = Integer.parseInt(ID);
        }
        catch (Exception e){
            e.printStackTrace();
            return entity;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            entity = (IEntity) session.load(getEntityClass(table), id);
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
        return entity;
    }
}
