package entity;

import core.RoomType;

public class Room {
    private int id;
    private int hotelId;
    private int pensionId;
    private RoomType type;
    private int stock;
    private int bedCount;;
    private int size;
    private boolean hasTV;
    private boolean hasMinibar;
    private boolean hasGameConsole;
    private boolean hasSafe;
    private boolean hasProjector;

    public Room() {
    }

    public Room(int id, int hotelId, int pensionId, RoomType type, int stock, int bedCount, int size, boolean hasTV, boolean hasMinibar, boolean hasGameConsole, boolean hasProjector, boolean hasSafe) {
        this.id = id;
        this.hotelId = hotelId;
        this.pensionId = pensionId;
        this.type = type;
        this.stock = stock;
        this.bedCount = bedCount;
        this.size = size;
        this.hasTV = hasTV;
        this.hasMinibar = hasMinibar;
        this.hasGameConsole = hasGameConsole;
        this.hasProjector = hasProjector;
        this.hasSafe = hasSafe;
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

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean hasTV() {
        return hasTV;
    }

    public void setHasTV(boolean hasTV) {
        this.hasTV = hasTV;
    }

    public boolean hasMinibar() {
        return hasMinibar;
    }

    public void setHasMinibar(boolean hasMinibar) {
        this.hasMinibar = hasMinibar;
    }

    public boolean hasGameConsole() {
        return hasGameConsole;
    }

    public void setHasGameConsole(boolean hasGameConsole) {
        this.hasGameConsole = hasGameConsole;
    }

    public boolean hasSafe() {
        return hasSafe;
    }

    public void setHasSafe(boolean hasSafe) {
        this.hasSafe = hasSafe;
    }

    public boolean hasProjector() {
        return hasProjector;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

}
