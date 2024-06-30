package model;

public class ResidentialCustomer extends Customers{
    private String lawnCarePackage;
    public ResidentialCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String customerType, String lawnCarePackage) {
        super(customerId, customerName, address, postalCode, phone, divisionId, customerType);
        this.lawnCarePackage = lawnCarePackage;
    }

    public ResidentialCustomer(String customerName, String address, String postalCode, String phone, int divisionId, String customerType, String lawnCarePackage) {
        super(customerName, address, postalCode, phone, divisionId, customerType);
        this.lawnCarePackage = lawnCarePackage;
    }

    public String getLawnCarePackage() {
        return lawnCarePackage;
    }

    public void setLawnCarePackage(String lawnCarePackage) {
        this.lawnCarePackage = lawnCarePackage;
    }
}
