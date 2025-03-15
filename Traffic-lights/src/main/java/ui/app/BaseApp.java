package ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseApp extends Application {

    protected void launchScene(String fxmlPath, Object presenter, String windowTitle, Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setController(presenter);
        Parent root = fxmlLoader.load();
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public abstract void start(Stage stage) throws Exception;
}