package lk.Ijse.thigakade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String []arge){ launch(arge);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/loginpage_form.fxml"))));
        stage.setTitle("dashBoard Form");
        stage.centerOnScreen();

        stage.show();
    }
}
