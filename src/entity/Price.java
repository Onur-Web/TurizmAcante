package entity;

public class Price {
    private int id;
    private int hotelId;
    private int pensionId;
    private int roomId;
    private int seasonId;
    private double adultPrice;
    private double childPrice;

    public Price() {
    }

    public Price(int id, int hotelId, int pensionId, int roomId, int seasonId, double adultPrice, double childPrice) {
        this.id = id;
        this.hotelId = hotelId;
        this.pensionId = pensionId;
        this.roomId = roomId;
        this.seasonId = seasonId;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getPensionId() {
        return pensionId;
    }

    public void setPensionId(int pensionId) {
        this.pensionId = pensionId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }
}
