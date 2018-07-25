// Core Model
package gdprdashboard;

public class Core implements ModelInterface {
    private UUID id;
    private double dateCreated;
    public Policy() {
        this.id = CommonService.getUUID();
        this.dateCreated = CommonService.getCurrentTimeStamp();
    }

    public UUID getId(){
        return this.id;
    }

    public double getDateCreated(){
        return this.dateCreated;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
}