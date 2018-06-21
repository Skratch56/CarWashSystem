
package carwashsystem.servicelist;

/**
 *
 * @author Joseph
 */
public class ServiceList {
    private int serviceCode;
    private String sType;
    private double cost;

    public ServiceList(int serviceCode,String sType, double cost) {
        this.sType = sType;
        this.cost = cost;
        this.serviceCode = serviceCode;
    }

    public String getsType() {
        return sType;
    }

    public double getCost() {
        return cost;
    }  

    public int getServiceCode() {
        return serviceCode;
    }
    
    
}
