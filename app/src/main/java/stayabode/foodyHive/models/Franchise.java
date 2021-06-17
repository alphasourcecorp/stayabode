package stayabode.foodyHive.models;

public class Franchise {

    String name;
    String location;
    Boolean status;
    String image;
    String date;
    String amount;
    String id;
    String addressLine1;
    String addressLine2;
    String pinCode;
    String state;
    String country;
    String startDate;
    String contact;
    String email;
    String uploadLogo;
    String gstNumber;
    String panNumber;
    String numberOfChefs;
    String numberOfCloudKitchens;
    String bankName;
    String bankbranchName;
    String accountNumber;
    String ifscCode;
    String certificates;
    String fssaiNumber;
    String contribution;

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUploadLogo() {
        return uploadLogo;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public void setUploadLogo(String uploadLogo) {
        this.uploadLogo = uploadLogo;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getNumberOfChefs() {
        return numberOfChefs;
    }

    public void setNumberOfChefs(String numberOfChefs) {
        this.numberOfChefs = numberOfChefs;
    }

    public String getNumberOfCloudKitchens() {
        return numberOfCloudKitchens;
    }

    public void setNumberOfCloudKitchens(String numberOfCloudKitchens) {
        this.numberOfCloudKitchens = numberOfCloudKitchens;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankbranchName() {
        return bankbranchName;
    }

    public void setBankbranchName(String bankbranchName) {
        this.bankbranchName = bankbranchName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public String getFssaiNumber() {
        return fssaiNumber;
    }

    public void setFssaiNumber(String fssaiNumber) {
        this.fssaiNumber = fssaiNumber;
    }

    public String getChoosePricingModel() {
        return choosePricingModel;
    }

    public void setChoosePricingModel(String choosePricingModel) {
        this.choosePricingModel = choosePricingModel;
    }

    String choosePricingModel;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String pricingModelID;
    String pricing;
    String pricingAmount;
    String pricingTransactions;
    String pricingTransactionPercentage;
    String pricinggstExtra;

    public String getPricingModelID() {
        return pricingModelID;
    }

    public void setPricingModelID(String pricingModelID) {
        this.pricingModelID = pricingModelID;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getPricingAmount() {
        return pricingAmount;
    }

    public void setPricingAmount(String pricingAmount) {
        this.pricingAmount = pricingAmount;
    }

    public String getPricingTransactions() {
        return pricingTransactions;
    }

    public void setPricingTransactions(String pricingTransactions) {
        this.pricingTransactions = pricingTransactions;
    }

    public String getPricingTransactionPercentage() {
        return pricingTransactionPercentage;
    }

    public void setPricingTransactionPercentage(String pricingTransactionPercentage) {
        this.pricingTransactionPercentage = pricingTransactionPercentage;
    }

    public String getPricinggstExtra() {
        return pricinggstExtra;
    }

    public void setPricinggstExtra(String pricinggstExtra) {
        this.pricinggstExtra = pricinggstExtra;
    }
}
