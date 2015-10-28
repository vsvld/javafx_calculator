package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
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

    private double firstNumber, secondNumber, result;

    @FXML
    private void handleButtonAction(ActionEvent e) {
        Button btn = (Button) e.getSource();

        switch (btn.getText()) {
            case "C":
                firstNumber = 0;
                showNumber(firstNumber);
                break;
        }
    }

    private void showNumber(double number) {
        textField.setText(Double.toString(number));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
