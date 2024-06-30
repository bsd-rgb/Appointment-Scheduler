package model;

public class CommercialCustomer extends Customers{

    private String contractType;
    public CommercialCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String customerType, String contractType) {
        super(customerId, customerName, address, postalCode, phone, divisionId, customerType);
        this.contractType = contractType;
    }

    public CommercialCustomer(String customerName, String address, String postalCode, String phone, int divisionId, String customerType, String contractType) {
        super(customerName, address, postalCode, phone, divisionId, customerType);
        this.contractType = contractType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}
