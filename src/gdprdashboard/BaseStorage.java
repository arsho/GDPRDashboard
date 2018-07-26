package gdprdashboard;

public interface BaseStorage
{
    public ArrayList<Core> getData();
    public void addData(Core instance);
    public void deleteData(Core instance);
}
 