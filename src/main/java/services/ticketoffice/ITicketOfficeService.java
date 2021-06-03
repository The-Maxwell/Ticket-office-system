package services.ticketoffice;

import entities.IEntity;

import java.util.List;

public interface ITicketOfficeService {
    List<IEntity> selectEntities(String itemId);
    String insertEntity(String entityString, String table);
    String updateEntity(String entityString, String table);
    String deleteEntity(String ID, String table);
    List<IEntity> searchBySpecificParams(String table, String... param);
    IEntity searchEntity(String ID, String table);
}
