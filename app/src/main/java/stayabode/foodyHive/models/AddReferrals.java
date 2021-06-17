package stayabode.foodyHive.models;

public class AddReferrals {

    private String ReferredByEmailId;
    private String ReferredByPhoneNumber;
    private String ReferredByName;
    private String ReferredToEmailId;
    private String ReferredToPhoneNumber;
    private String ReferredToName;
    private String ReferralCode;
    private String ReferralDate;
    private int ReferralAmount;

    public String getReferredByEmailId() {
        return ReferredByEmailId;
    }

    public void setReferredByEmailId(String referredByEmailId) {
        ReferredByEmailId = referredByEmailId;
    }

    public String getReferredByPhoneNumber() {
        return ReferredByPhoneNumber;
    }

    public void setReferredByPhoneNumber(String referredByPhoneNumber) {
        ReferredByPhoneNumber = referredByPhoneNumber;
    }

    public String getReferredByName() {
        return ReferredByName;
    }

    public void setReferredByName(String referredByName) {
        ReferredByName = referredByName;
    }

    public String getReferredToEmailId() {
        return ReferredToEmailId;
    }

    public void setReferredToEmailId(String referredToEmailId) {
        ReferredToEmailId = referredToEmailId;
    }

    public String getReferredToPhoneNumber() {
        return ReferredToPhoneNumber;
    }

    public void setReferredToPhoneNumber(String referredToPhoneNumber) {
        ReferredToPhoneNumber = referredToPhoneNumber;
    }

    public String getReferredToName() {
        return ReferredToName;
    }

    public void setReferredToName(String referredToName) {
        ReferredToName = referredToName;
    }

    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String referralCode) {
        ReferralCode = referralCode;
    }

    public String getReferralDate() {
        return ReferralDate;
    }

    public void setReferralDate(String referralDate) {
        ReferralDate = referralDate;
    }

    public int getReferralAmount() {
        return ReferralAmount;
    }

    public void setReferralAmount(int referralAmount) {
        ReferralAmount = referralAmount;
    }




    // Constructor of the class
    // to initialize the variable*
    public AddReferrals(String ReferredByEmailId, String ReferredByPhoneNumber, String ReferredByName,String ReferredToEmailId,String ReferredToPhoneNumber,String ReferredToName,String ReferralCode,String ReferralDate,int ReferralAmount)
    {
        this.ReferredByEmailId = ReferredByEmailId;
        this.ReferredByPhoneNumber = ReferredByPhoneNumber;
        this.ReferredByName = ReferredByName;
        this.ReferredToEmailId = ReferredToEmailId;
        this.ReferredToPhoneNumber = ReferredToPhoneNumber;
        this.ReferredToName = ReferredToName;
        this.ReferralCode = ReferralCode;
        this.ReferralDate = ReferralDate;
        this.ReferralAmount = ReferralAmount;
    }







}
