package carwashsystem;

import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Joseph
 */
public class LoginController implements Initializable {

    private Label label;
    private AnchorPane loginForm;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<String> cmbType;
    private Label lblUsernameError;
    private Label lblPassworderror;
    private LoginModel modelLogin;
    private static Connection con = null;
    private static PreparedStatement pr;
    private static Statement st;
    private static ResultSet rs;
    public static int globalEmployeeId;
    public static String globalEmployeeType;
    private final int MAX_ATTEMPT = 3;
    private int numAttempts = 1;
    @FXML
    private JFXButton createLogin;
    @FXML
    private StackPane loginStack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!CarWashSystem.isOpenSplash) {
            openSplash();
        }
        populate();
        try {
            modelLogin = new LoginModel();
        } catch (Exception e) {
            AlertMessages.getError("Connection err", e.getMessage());
        }
    }

    void openSplash() {
        CarWashSystem.isOpenSplash = true;
        try {
            StackPane splashPane = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
            loginStack.getChildren().setAll(splashPane);
            FadeTransition startFade = new FadeTransition(Duration.seconds(2), splashPane);
            startFade.setFromValue(0);
            startFade.setToValue(1);
            startFade.setCycleCount(1);

            FadeTransition endFade = new FadeTransition(Duration.seconds(2), splashPane);
            endFade.setFromValue(1);
            endFade.setToValue(0);
            endFade.setCycleCount(1);
            startFade.play();

            startFade.setOnFinished(ev -> {
                endFade.play();
            });

            endFade.setOnFinished(ev -> {
                try {
                    StackPane loginPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    loginStack.getChildren().setAll(loginPane);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createLogin(ActionEvent event) {
        Login objLogin;
        String username, password, type;
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()
                || cmbType.getValue() == null) {
            AlertMessages.getWarning("Null", "All fields are required. Please enter your login details");
        } else {
            username = txtUsername.getText();
            password = txtPassword.getText();
            type = cmbType.getValue();

            objLogin = new Login(username, password, type);

            try {
                if (numAttempts < MAX_ATTEMPT) {
                    if (objLogin.isLogin(objLogin)) {
                        globalEmployeeId = Integer.parseInt(objLogin.returnEmp(objLogin));
                        globalEmployeeType = type;
                        openDashboard(event);

                        //closeForm();
                    } else {
                        AlertMessages.getError("unsuccessful", "Logged in failed");
                    }
                } else if (numAttempts != MAX_ATTEMPT) {
                    AlertMessages.getError("Login authentication", "Try once again " + numAttempts);

                } else {
                    AlertMessages.getError("Login authentication", "Attempts exceeded " + numAttempts);
                    txtUsername.setDisable(true);
                    txtPassword.setEditable(false);
                }
                if (numAttempts == 3) {
                    //Platform.exit();
                    //openSplash();
                    openSplashAfterFailed();
                }
                numAttempts++;
            } catch (UnknownException ue) {
                AlertMessages.getError("Null", ue.getMessage());
            }

        }
    }

    private void checkUsername(ActionEvent event) {
        if (txtUsername.getText().isEmpty()) {
            lblUsernameError.setText("Please enter username");
            lblUsernameError.setStyle("-fx-text-fill: red");
            lblUsernameError.setStyle("-fx-font-size: 18");

        }
    }

    private void checkPassword(ActionEvent event) {
        if (txtPassword.getText().isEmpty()) {
            lblPassworderror.setText("Please enter password");
            lblPassworderror.setStyle("-fx-text-fill: red");
            lblPassworderror.setStyle("-fx-font-size: 18");

        }

    }

    void openSplashAfterFailed() {
        CarWashSystem.isOpenSplash = true;
        try {
            StackPane splashPane = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
            loginStack.getChildren().setAll(splashPane);
            FadeTransition startFade = new FadeTransition(Duration.seconds(5), splashPane);
            startFade.setFromValue(0);
            startFade.setToValue(1);
            startFade.setCycleCount(1);

            FadeTransition endFade = new FadeTransition(Duration.seconds(5), splashPane);
            endFade.setFromValue(1);
            endFade.setToValue(0);
            endFade.setCycleCount(1);
            startFade.play();

            startFade.setOnFinished(ev -> {
                endFade.play();
            });

            endFade.setOnFinished(ev -> {
                try {
                    StackPane loginPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    loginStack.getChildren().setAll(loginPane);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * populate employee type combo box
     */
    public void populate() {
        ObservableList<String> empTypes = FXCollections.observableArrayList();
        empTypes.add("Manager");

        empTypes.add("Cashier");
        cmbType.setItems(empTypes);
    }

    /*
    open dashboard
     */
    public void openDashboard(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    /**
     * close form
     */
    String getEmpData() throws DataStorageException {
        String qry = "SELECT firstname,surname FROM employee WHERE username=? AND password= ?";
        String name = "";
        String surname = "";
        try {
            con = CreateConnection.initialise();
            pr = con.prepareStatement(qry);
            pr.setString(1, txtUsername.getText().trim());
            pr.setString(2, txtPassword.getText().trim());

            rs = pr.executeQuery();
            if (rs.next()) {

                name = rs.getString("firstname");
                surname = rs.getString("surname");

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Welcome " + name + " " + surname;
    }

    public void closeForm() {
        System.exit(0);
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        closeForm();
    }

}
