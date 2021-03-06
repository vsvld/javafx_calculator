package sample;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    private boolean apiModeOn = true;

    @FXML
    private void handleButtonAction(ActionEvent e) throws IOException {

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
                if (apiModeOn) activeNumber.setValue(getApiResult("square_root"));
                else activeNumber.setValue(Math.pow(activeNumber.getValue(), 0.5));
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
    private void calculateResult() throws IOException {

        switch (savedOperation.getValue()) {
            case "/":
                if (apiModeOn) result.setValue(getApiResult("division"));
                else result.setValue(result.getValue() / activeNumber.getValue());
                break;
            case "*":
                if (apiModeOn) result.setValue(getApiResult("multiplication"));
                else result.setValue(result.getValue() * activeNumber.getValue());
                break;
            case "-":
                if (apiModeOn) result.setValue(getApiResult("subtraction"));
                else result.setValue(result.getValue() - activeNumber.getValue());
                break;
            case "+":
                if (apiModeOn) result.setValue(getApiResult("addition"));
                else result.setValue(result.getValue() + activeNumber.getValue());
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

    private double getApiResult(String operation_str) throws IOException {
        String n1_str = Double.toString(result.getValue());
        String n2_str = Double.toString(activeNumber.getValue());
        String url = "http://localhost:3000/calculator/" + operation_str + "?n1=" + n1_str + "&n2=" + n2_str;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + con.getResponseCode());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuffer response = new StringBuffer();

            String request_result = in.readLine();
            System.out.println(result);
            return Double.parseDouble(request_result);
        }
    }
}
