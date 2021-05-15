package entities;

import java.util.List;

public interface IEntity {
    public List<String> recieveEntityInfo();
    public String[] recieveColumnsName();
    public String recieveStringInfo();
}
