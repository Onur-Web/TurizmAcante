package view.management.hotel;

import business.PensionManager;
import core.PensionType;
import core.Helper;
import entity.Pension;
import view.Layout;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PensionPopup extends Layout {
    private JPanel container;
    private JCheckBox checkBox_ultra_all_included;
    private JCheckBox checkBox_all_included;
    private JCheckBox checkBox_room_breakfast;
    private JCheckBox checkBox_full_pension;
    private JCheckBox checkBox_half_pension;
    private JCheckBox checkBox_only_bed;
    private JCheckBox checkBox_non_alcohol_full_credit;
    private JButton button_update_pensions;


    private int hotelId;

    private PensionManager pensionManager;

    private Map<JCheckBox, PensionType> map;


    PensionPopup(int hotelId) {
        initializeView(400, 300, "Update Pension");
        add(container);

        this.hotelId = hotelId;
        this.pensionManager = PensionManager.getInstance();
        this.map = new HashMap<>();

        map.put(checkBox_ultra_all_included, PensionType.ULTRA_ALL_INCLUDED);
        map.put(checkBox_all_included, PensionType.ALL_INCLUDED);
        map.put(checkBox_room_breakfast, PensionType.ROOM_BREAKFAST);
        map.put(checkBox_full_pension, PensionType.FULL_PENSION);
        map.put(checkBox_half_pension, PensionType.HALF_PENSION);
        map.put(checkBox_only_bed, PensionType.ONLY_BED);
        map.put(checkBox_non_alcohol_full_credit, PensionType.NON_ALCOHOL_FULL_CREDIT);

        fillCheckBoxes();
        initializeUpdateButtonListener();
    }

    private void fillCheckBoxes() {
        List<Pension> pensions = pensionManager.getPensionsByHotelId(hotelId);

        map.forEach(
                (key, value) -> {
                    boolean isPresent = pensions.stream().anyMatch(pension -> value.equals(pension.getType()));

                    key.setSelected(isPresent);
                }
        );

    }

    private void initializeUpdateButtonListener() {
        button_update_pensions.addActionListener(e -> {
            map.forEach(
                    (key, value) -> {

                        Optional<Pension> pension = pensionManager.getPensionByHotelIdAndType(hotelId, value);

                        if (key.isSelected() && pension.isEmpty()) {

                            pensionManager.create(new Pension(0, hotelId, value));

                        } else if (!key.isSelected() && pension.isPresent()) {

                            pensionManager.delete(pension.get().getId());

                        }

                    });

            Helper.showMassageAll("done");

            dispose();
        });
    }


}
