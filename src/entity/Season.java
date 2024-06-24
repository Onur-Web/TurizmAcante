package entity;

import core.SeasonType;

import java.sql.Date;

public class Season {
    private int id;
    private int hotelId;
    private SeasonType type;
    private Date start;
    private Date finish;

    public Season() {
    }

    public Season(int id, int hotelId, SeasonType type, Date start, Date finish) {
        this.id = id;
        this.hotelId = hotelId;
        this.type = type;
        this.start = start;
        this.finish = finish;
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

    public SeasonType getType() {
        return type;
    }

    public void setType(SeasonType type) {
        this.type = type;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
