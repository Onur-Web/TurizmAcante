package view.management.hotel;

import business.FacilityManager;
import core.FacilityType;
import core.Helper;
import entity.Facility;
import view.Layout;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class FacilityPopup extends Layout {
    private JPanel container;
    private JCheckBox checkBox_free_parking;
    private JCheckBox checkBox_swimming_pool;
    private JCheckBox checkBox_hotel_concierge;
    private JCheckBox checkBox_room_service;
    private JCheckBox checkBox_free_wifi;
    private JCheckBox checkBox_fitness_center;
    private JCheckBox checkBox_spa;
    private JButton button_update_facilities;


    private int hotelId;

    private FacilityManager facilityManager;

    private Map<JCheckBox, FacilityType> map;


    FacilityPopup(int hotelId) {
        initializeView(400, 300, "Update Pension");
        add(container);

        this.hotelId = hotelId;
        this.facilityManager = FacilityManager.getInstance();
        this.map = new HashMap<>();

        map.put(checkBox_free_parking, FacilityType.FREE_PARKING);
        map.put(checkBox_swimming_pool, FacilityType.SWIMMING_POOL);
        map.put(checkBox_hotel_concierge, FacilityType.HOTEL_CONCIERGE);
        map.put(checkBox_room_service, FacilityType.ROOM_SERVICE);
        map.put(checkBox_free_wifi, FacilityType.FREE_WIFI);
        map.put(checkBox_fitness_center, FacilityType.FITNESS_CENTER);
        map.put(checkBox_spa, FacilityType.SPA);

        fillCheckBoxes();
        initializeUpdateButtonListener();
    }

    private void fillCheckBoxes() {
        List<Facility> facilities = facilityManager.getFacilitiesByHotelId(hotelId);

        // streamleri şöyle düşünebiliriz: lambda fonksiyonları ile beraber kullanılabilen bir liste (Predicate classları incele)
        // anyMatch metodu bir stream içerisinde belli bir lambda fonskiyonuna uyan bir obje var mı bunu kontrol etmek için kullanılır varsa true yoksa false döner
        // free parking bulunmuşsa boolean true olacak ve checkBox aynı şekilde tickli hale gelecek

        map.forEach(
            (key, value) -> {
                boolean isPresent = facilities.stream().anyMatch(facility -> value.equals(facility.getType()));

                key.setSelected(isPresent);
            }
        );

    }

    private void initializeUpdateButtonListener() {
        button_update_facilities.addActionListener(e -> {
            map.forEach(
                (key, value) -> {

                    Optional<Facility> facility = facilityManager.getFacilityByHotelIdAndType(hotelId, value);

                    if (key.isSelected() && facility.isEmpty()) {

                        facilityManager.create(new Facility(0, hotelId, value));

                    } else if (!key.isSelected() && facility.isPresent()) {

                        facilityManager.delete(facility.get().getId());

                    }

                });

            Helper.showMassageAll("done");

            dispose();
        });
    }
}
