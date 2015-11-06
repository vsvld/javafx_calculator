package sample;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

//    public StringProperty result = new SimpleStringProperty("0");

    // TODO variables declaration in one line if possible

    @FXML
    private TextField textField;

    // digits buttons
    @FXML
    private Button zeroButton;
    @FXML
    private Button oneButton;
    @FXML
    private Button twoButton;
    @FXML
    private Button threeButton;
    @FXML
    private Button fourButton;
    @FXML
    private Button fiveButton;
    @FXML
    private Button sixButton;
    @FXML
    private Button sevenButton;
    @FXML
    private Button eightButton;
    @FXML
    private Button nineButton;

    // actions with 2 numbers buttons
    @FXML
    private Button addButton;
    @FXML
    private Button subtractButton;
    @FXML
    private Button multiplyButton;
    @FXML
    private Button divideButton;

    // actions with 1 number buttons
    @FXML
    private Button invertButton;
    @FXML
    private Button commaButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button powerHalfButton;
    @FXML
    private Button equalsButton;

    private DoubleProperty activeNumber = new SimpleDoubleProperty();
    private DoubleProperty result = new SimpleDoubleProperty();
    private BooleanProperty isDecimal = new SimpleBooleanProperty();
    private BooleanProperty withPoint = new SimpleBooleanProperty();
    private StringProperty savedOperation = new SimpleStringProperty();

    @FXML
    private void handleButtonAction(ActionEvent e) {

        Button btn = (Button) e.getSource();

        switch (btn.getText()) {
            case "C":
                activeNumber.setValue(0);
                result.setValue(0);
                showNumber(activeNumber);
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (isDecimal.getValue() && !withPoint.getValue()) {
                    activeNumber.setValue(Double.parseDouble(fmt(activeNumber.getValue()) + '.' + btn.getText()));
                    withPoint.setValue(true);
                }
                else if (activeNumber.getValue() == 0) {
                    activeNumber.setValue(Integer.parseInt(btn.getText()));
                }
                else {
                    activeNumber.setValue(Double.parseDouble(fmt(activeNumber.getValue()) + btn.getText()));
                }
                showNumber(activeNumber);
                break;
            case ",":
                if (!isDecimal.getValue()) isDecimal.setValue(true);
                break;
            case "+/-":
                activeNumber.setValue(activeNumber.getValue() * -1);
                showNumber(activeNumber);
                break;
            case "a^1/2":
                activeNumber.setValue(Math.pow(activeNumber.getValue(), 0.5));
                showNumber(activeNumber);
                break;
            case "/":
            case "*":
            case "-":
            case "+":
                if (!Objects.equals(savedOperation.getValue(), "my_null")) {
                    calculateResult();
                    showNumber(result);
                } else {
                    result.setValue(activeNumber.getValue());
                }
                savedOperation.setValue(btn.getText());
                activeNumber.setValue(0);
                break;
            case "=":
                calculateResult();
                showNumber(result);
                activeNumber.setValue(0);
                result.setValue(0);
                break;
        }

        if (Double.isNaN(activeNumber.getValue())) {
            activeNumber.setValue(0);
            textField.setText("Invalid input");
        }

//        System.out.println("active");
//        System.out.println(activeNumber.getValue());
//        System.out.println("result");
//        System.out.println(result.getValue());
//        System.out.println(savedOperation.getValue());
    }

    private void showNumber(DoubleProperty number) {
        textField.setText(fmt(number.getValue()));
    }

    // calculates result between two numbers with operation saved
    private void calculateResult() {
        switch (savedOperation.getValue()) {
            case "/":
                result.setValue(result.getValue() / activeNumber.getValue());
                break;
            case "*":
                result.setValue(result.getValue() * activeNumber.getValue());
                break;
            case "-":
                result.setValue(result.getValue() - activeNumber.getValue());
                break;
            case "+":
                result.setValue(result.getValue() + activeNumber.getValue());
                break;
        }
        savedOperation.setValue("my_null");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeNumber.setValue(0);
        result.setValue(0);
        isDecimal.setValue(false);
        withPoint.setValue(false);
        savedOperation.setValue("my_null");
        showNumber(activeNumber);
    }

    public static String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }
}
