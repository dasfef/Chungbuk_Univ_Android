public class TravelData {

    public String district;
    public String region;
    public String rcmReason;

    public TravelData(String district, String region, String rcmReason){
        this.district=district;
        this.region=region;
        this.rcmReason=rcmReason;
    }

    public String getDistrict(){
        return district;
    }
    public String getRegion(){
        return region;
    }
    public String getRcmReason(){
        return rcmReason;
    }
}
