package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartMenuApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/StartMenu.fxml"));
        StartMenuPresenter presenter = new StartMenuPresenter();
        fxmlLoader.setController(presenter);
        Parent root = fxmlLoader.load();
        stage.setTitle("Start");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
