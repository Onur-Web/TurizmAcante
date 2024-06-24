package view.management.hotel;

import business.SeasonManager;
import core.Helper;
import core.SeasonType;
import entity.Season;
import view.Layout;

import javax.swing.*;
import java.sql.Date;
import java.util.*;

public class SeasonPopup extends Layout {
    private JPanel container;
    private JTextField textField_winter_start;
    private JTextField textField_winter_finish;
    private JTextField textField_summer_start;
    private JTextField textField_summer_finish;
    private JButton button_submit;
    private JLabel label_winter;
    private JLabel label_winter_start;
    private JLabel label_winter_finish;
    private JLabel label_summer;
    private JLabel label_summer_start;
    private JLabel label_summer_finish;


    private int hotelId;

    private SeasonManager seasonManager;

    public SeasonPopup(int hotelId) {
        initializeView(440, 300, "Update Season");
        add(container);

        this.hotelId = hotelId;
        this.seasonManager = SeasonManager.getInstance();

        fillFields();

        initializeSubmitButtonListener();
    }

    private void fillFields() {
        List<Season> seasons = seasonManager.getSeasonsByHotelId(hotelId);

        if (seasons.isEmpty()) {
            return;
        }

        Season winter = seasons.stream()
                .filter(season -> SeasonType.WINTER.equals(season.getType()))
                .findAny()
                .orElse(null);

        textField_winter_start.setText(winter.getStart().toString());
        textField_winter_finish.setText(winter.getFinish().toString());

        Season summer = seasons.stream()
                .filter(season -> SeasonType.SUMMER.equals(season.getType()))
                .findAny()
                .orElse(null);

        textField_summer_start.setText(summer.getStart().toString());
        textField_summer_finish.setText(summer.getFinish().toString());
    }

    private void initializeSubmitButtonListener() {
        button_submit.addActionListener(e -> {
            JTextField[] fields = {textField_winter_start, textField_winter_finish, textField_summer_start, textField_summer_finish};

            if (Helper.isFieldListEmpty(fields)) {
                Helper.showMassageAll("fill");
                return;
            }

            Optional<Season> winter = seasonManager.getSeasonByHotelIdAndType(hotelId, SeasonType.WINTER);

            if (winter.isPresent()) {
                winter.get().setStart(Date.valueOf(textField_winter_start.getText()));
                winter.get().setFinish(Date.valueOf(textField_winter_finish.getText()));

                seasonManager.update(winter.get());
            } else {

                seasonManager.create(new Season(
                                0,
                                hotelId,
                                SeasonType.WINTER,
                                Date.valueOf(textField_winter_start.getText()),
                                Date.valueOf(textField_winter_finish.getText())
                        )
                );
            }

            Optional<Season> summer = seasonManager.getSeasonByHotelIdAndType(hotelId, SeasonType.SUMMER);

            if (summer.isPresent()) {
                summer.get().setStart(Date.valueOf(textField_summer_start.getText()));
                summer.get().setFinish(Date.valueOf(textField_summer_finish.getText()));

                seasonManager.update(summer.get());
            } else {

                Season season = new Season(
                        0,
                        hotelId,
                        SeasonType.SUMMER, Date.valueOf(textField_summer_start.getText()),
                        Date.valueOf(textField_summer_finish.getText())
                );

                seasonManager.create(season);
            }

            dispose();
        });
    }
}
