package entity;

import core.FacilityType;

public class Facility {
    private int id;
    private int hotelId;
    private FacilityType type;

    public Facility() {
    }

    public Facility(int id, int hotelId, FacilityType type) {
        this.id = id;
        this.hotelId = hotelId;
        this.type = type;
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

    public FacilityType getType() {
        return type;
    }

    public void setType(FacilityType type) {
        this.type = type;
    }
}
