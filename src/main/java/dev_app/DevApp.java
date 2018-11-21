package dev_app;

import db.DBProcessor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DevApp  extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage) throws IOException {
        //File file = new File();
        //URL url = new File("src/main/java/bank/view/bank_app.fxml").toURL();
        URL url = new File("/Users/mark/Documents/projects/development/src/main/java/dev_app/view/deb_app.fxml").toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DBProcessor.getStatement().close();
        DBProcessor.getConnection().close();
    }
}
