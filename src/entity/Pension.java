package entity;

import core.PensionType;

public class Pension {
    private int id;
    private int hotelId;
    private PensionType type;

    public Pension() {
    }

    public Pension(int id, int hotelId, PensionType type) {
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

    public PensionType getType() {
        return type;
    }

    public void setType(PensionType type) {
        this.type = type;
    }
}
