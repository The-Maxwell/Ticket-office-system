package business;

import entities.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

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
                case "user":
                    query = session.createNamedQuery("SelectAllUser");
                    break;
            }
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public String insertEntity(String entityString, String table) {
        String result = null;
        entityString = entityString.trim();
        String[] arrEntityString = entityString.split(",");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(getNeededEntityForInsert(arrEntityString, session, table));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return result;
        }
    }

    public String updateEntity(String entityString, String table) {
        String result = null;
        entityString = entityString.trim();
        String[] arrEntityString = entityString.split(",");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(getNeededEntityForUpdate(arrEntityString, session, table));
            session.getTransaction().commit();
        } catch (Exception e) {
            result = e.getMessage();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return result;
        }
    }

    public String deleteEntity(String ID, String table) {
        String result = null;
        int id;
        try {
            id = Integer.parseInt(ID);
        } catch (Exception e) {
            return "Error. Empty input!";
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            IEntity entity = (IEntity) session.load(getEntityClass(table), id);
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            result = e.getMessage();
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
            case "user":
            case "authorization":
                return UserEntity.class;
            default:
                return null;
        }
    }

    public Object getNeededEntityForInsert(String[] arrEntityString, Session session, String table) throws Exception {
        switch (table) {
            case "vehicle":
                return new VehicleEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], arrEntityString[5], arrEntityString[6]);
            case "journary":
                int idVehicle = Integer.parseInt(arrEntityString[4]);
                return new JournaryEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], session.load(VehicleEntity.class, idVehicle));
            case "receipt":
                int idPassenger = Integer.parseInt(arrEntityString[3]);
                int idUser = Integer.parseInt(arrEntityString[4]);
                return new ReceiptEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], session.load(PassengerEntity.class, idPassenger), session.load(UserEntity.class, idUser));
            case "ticket":
                int idReceipt = Integer.parseInt(arrEntityString[3]);
                int idJournary = Integer.parseInt(arrEntityString[4]);
                return new TicketEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], session.load(ReceiptEntity.class, idReceipt), session.load(JournaryEntity.class, idJournary));
            case "passenger":
                return new PassengerEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3]);
            case "user":
                return new UserEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], arrEntityString[5], arrEntityString[6], DigestUtils.sha256Hex(arrEntityString[7] + IEntity.SAULT));
            default:
                return null;
        }
    }

    public Object getNeededEntityForUpdate(String[] arrEntityString, Session session, String table) throws Exception {
        switch (table) {
            case "vehicle":
                return new VehicleEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], arrEntityString[5], arrEntityString[6]);
            case "journary":
                int idVehicle = Integer.parseInt(arrEntityString[5]);
                return new JournaryEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], session.load(VehicleEntity.class, idVehicle), arrEntityString[6]);
            case "receipt":
                int idPassenger = Integer.parseInt(arrEntityString[4]);
                int idUser = Integer.parseInt(arrEntityString[5]);
                return new ReceiptEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], session.load(PassengerEntity.class, idPassenger), session.load(UserEntity.class, idUser));
            case "ticket":
                int idReceipt = Integer.parseInt(arrEntityString[4]);
                int idJournary = Integer.parseInt(arrEntityString[5]);
                return new TicketEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], session.load(ReceiptEntity.class, idReceipt), session.load(JournaryEntity.class, idJournary));
            case "passenger":
                return new PassengerEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4]);
            case "user":
                idUser = Integer.parseInt(arrEntityString[0]);
                UserEntity userEntity = ((UserEntity) session.load(UserEntity.class, idUser));
                String pwd = userEntity.getPassword();
                session.evict(userEntity);
                return new UserEntity(arrEntityString[0], arrEntityString[1], arrEntityString[2], arrEntityString[3], arrEntityString[4], arrEntityString[5], arrEntityString[6], arrEntityString[7], pwd);
            default:
                return null;
        }
    }

    public List<IEntity> searchBySpecificParams(String table, String... param) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List result = null;
        try {
            session.beginTransaction();

            Criteria criteria = getNeededCriteriaForSearch(session, table, param);
            result = criteria.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    private Criteria getNeededCriteriaForSearch(Session session, String table, String... param) throws Exception {
        switch (table) {
            case "vehicle":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("vehicleType", param[0]));
            case "journary":
                Criteria criteria = session.createCriteria(getEntityClass(table));
                if (!param[0].equals(""))
                    criteria.add(Restrictions.like("departurePoint", "%" + param[0] + "%"));
                if (!param[1].equals(""))
                    criteria.add(Restrictions.eq("dateAndTimeOfArrival", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S").parse(param[1])));
                return criteria;
            case "receipt":
                criteria = session.createCriteria(getEntityClass(table));
                if (!param[0].equals(""))
                    criteria.add(Restrictions.eq("passengerByPassengerId", searchEntity(param[0], table)));
                return criteria;
            case "ticket":
            case "passenger":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("category", param[0]));
            case "user":
                criteria = session.createCriteria(getEntityClass(table));
                if (!param[0].trim().equals(""))
                    criteria.add(Restrictions.like("lastName", "%" + param[0] + "%"));
                if (!param[1].trim().equals(""))
                    criteria.add(Restrictions.like("firstName", "%" + param[1] + "%"));
                if (param[2] != null)
                    criteria.add(Restrictions.eq("role", param[2]));
                return criteria;
            case "authorization":
                return session.createCriteria(getEntityClass(table)).add(Restrictions.eq("email", param[0]))
                        .add(Restrictions.eq("password", param[1]));
        }
        return null;
    }

    public IEntity searchEntity(String ID, String table) {
        IEntity entity = null;
        int id;
        try {
            id = Integer.parseInt(ID);
        } catch (Exception e) {
            e.printStackTrace();
            return entity;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            entity = (IEntity) session.load(getEntityClass(table), id);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return entity;
    }
}
