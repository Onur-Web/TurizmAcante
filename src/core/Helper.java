package core;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class Helper {
    //TEMAYI APPA EKLEMEK
    public static void setTheme(String layoutName) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (layoutName.equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    //APPI EKRANDA ORTALAMA
    public static int getScreenCenter(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    //TEK BIR FIELDDAKI BILESENIN BOS OLUP OLMADIGINI KONTROL EDER VE TEXTLERI AL - BOSLUKLARINI SIL VE EGER BOS ISE TRUE DON
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    //FIELDDAKI HER BILESENI DON BOS ISE TRUE DON
    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field)) return true;
        }

        return false;
    }

    //KULLANICIYA YAPILAN ISLEMDEN EMINMISIN DIYE SORMAK
    public static boolean showMassageSure(String messageType) {
        optionPane();
        String message;

        switch (messageType) {
            case "sure":
                message = "Are You Sure You Want To Perform This Operation?";
                break;
            default:
                message = messageType;
        }

        return JOptionPane.showConfirmDialog(null, message, "Warning !!!", JOptionPane.YES_NO_OPTION) == 0;
    }

    //DURUMA GORE KULLANICIYA CIKTI GOSTERME
    public static boolean showMassageAll(String messageType) {
        optionPane();
        String message;
        String title ;

        switch (messageType) {
            case "done":
                message = "Your Transaction Was Successful.";
                title = "";
                break;
            case"logSuccessful":
                message = "You Have Successfully Logged Into The System.";
                title = "Welcome";
                break;
            case "fill":
                message = "Please Fill In All Fields.";
                title = "Error !!!";
                break;
            case "notFound":
                message = "Data Record Not Found";
                title = "Error !!!";
                break;
            case "error":
                message = "You Made An Incorrect Entry";
                title = "Error !!!";
                break;
            default:
                message = messageType;
                title = "Message";
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);

        return false;
    }

    //( OK - YES - NO ) ISTEDIGIMIZ GIBI TANIMLAMA
    public static void optionPane() {
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.yesButtonText", "YES");
        UIManager.put("OptionPane.noButtonText", "NO");
    }

    public static boolean isComboBoxEmpty(JComboBox comboBox) {
        return comboBox.getModel().getSelectedItem().equals("");
    }

    public static boolean isComboBoxListEmpty(JComboBox[] comboBoxes) {
        for (JComboBox comboBox : comboBoxes) {
            if (isComboBoxEmpty(comboBox))
                return true;
        }

        return false;
    }

}
