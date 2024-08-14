package lk.ijse.fishmarketing.Utill;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text) {
        String field = "";

        switch (textField) {
            case ID:
                field = "^([A-Z0-9]){4}$";
                break;
            case NAME:
                field = "^[A-z|\\s]{3,}$";
                break;
            case ADDRESS:
                field = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case TEL:
                field = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case QTY:
                field = "^\\d+$";
                break;
            case PRICE:
                field = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
        }

        Pattern pattern = Pattern.compile(field);

        if (text != null) {
            if (text.trim().isEmpty()) {
                return false;
            }
        } else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-focus-color: #5aec5a;");
            return true;
        }else {
            textField.setStyle("-fx-border-color: #f85454;-fx-border-radius: 5;-fx-border-width: 3;");
            return false;
        }
    }
}
