package services.ticketoffice;

import entities.IEntity;
import utils.TicketOfficeDao;

import java.util.List;

public class TicketOfficeServiceImpl implements ITicketOfficeService{
    private TicketOfficeDao ticketOfficeDao;

    public TicketOfficeServiceImpl() {
        this.ticketOfficeDao = new TicketOfficeDao();
    }

    @Override
    public List<IEntity> selectEntities(String itemId) {
        return ticketOfficeDao.selectEntities(itemId);
    }

    @Override
    public String insertEntity(String entityString, String table) {
        System.out.println("Insert");
        return ticketOfficeDao.insertEntity(entityString, table);
    }

    @Override
    public String updateEntity(String entityString, String table) {
        return ticketOfficeDao.updateEntity(entityString, table);
    }

    @Override
    public String deleteEntity(String ID, String table) {
        return ticketOfficeDao.deleteEntity(ID, table);
    }

    @Override
    public List<IEntity> searchBySpecificParams(String table, String... param) {
        return ticketOfficeDao.searchBySpecificParams(table, param);
    }

    @Override
    public IEntity searchEntity(String ID, String table) {
        return ticketOfficeDao.searchEntity(ID, table);
    }
}
