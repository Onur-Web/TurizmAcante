package business;

import dao.ReservationDAO;
import entity.Price;
import entity.Reservation;
import entity.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class ReservationManager {
    private final ReservationDAO reservationDAO;
    private static ReservationManager instance;
    private final PriceManager priceManager;
    private final RoomManager roomManager;

    private ReservationManager() {
        this.reservationDAO = ReservationDAO.getInstance();
        this.priceManager = PriceManager.getInstance();
        this.roomManager = RoomManager.getInstance();
    }

    public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }

        return instance;
    }

    public List<Reservation> getAll() {
        return reservationDAO.findAll();
    }

    public Optional<Reservation> getById(int id) {
        return reservationDAO.findById(id);
    }

    public boolean create(Reservation reservation) {
        reservation.setTotalPrice(calculatePrice(reservation));

        Optional<Room> optionalRoom = roomManager.getById(reservation.getRoomId());

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            room.setStock(room.getStock() - 1);

            roomManager.update(room);
        }

        return reservationDAO.create(reservation);
    }

    public boolean update(Reservation reservation) {
        reservation.setTotalPrice(calculatePrice(reservation));

        return reservationDAO.update(reservation);
    }

    public double calculatePrice(final Reservation reservation) {
        Optional<Room> optionalRoom = roomManager.getById(reservation.getRoomId());

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            Optional<Price> optionalPrice = priceManager.getByRoomIdAndPensionIdAndDates(room.getId(), room.getPensionId(), reservation.getStart(), reservation.getFinish());

            if (optionalPrice.isPresent()) {
                Price price = optionalPrice.get();
                // totalfiyat = ((adultPrice * adult sayısı) + (childPrice * child sayısı)) * kalınan gün sayısı

                LocalDate dateOne = reservation.getStart().toLocalDate();
                LocalDate dateTwo = reservation.getFinish().toLocalDate();

                int numberOfDays = (int) ChronoUnit.DAYS.between(dateOne, dateTwo);

                double totalPrice = ((price.getAdultPrice() * reservation.getNumberOfAdults()) + (price.getChildPrice() * reservation.getNumberOfChildren())) * numberOfDays;

                return totalPrice;
            }
        }

        return 0;
    }

    public boolean delete(int id) {
        Optional<Reservation> optionalReservation = getById(id);

        if (optionalReservation.isEmpty())
            return false;

        Optional<Room> optionalRoom = roomManager.getById(optionalReservation.get().getRoomId());

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            room.setStock(room.getStock() + 1);

            roomManager.update(room);
        }

        return reservationDAO.delete(id);
    }
}
