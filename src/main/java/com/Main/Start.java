package com.Main;


import com.Main.datafetch.DataService;
import com.Main.datafetch.model.CovidDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {
    private double xOffset;
    private double yOffset;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initOwner(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/com/Main/userinterface/fx/screen.fxml"));
        Scene scene = new Scene(root);
        secondaryStage.setScene(scene);
        secondaryStage.show();

        //CovidDataModel dataModel =new DataService().getData("Turkey");
        //System.out.println(dataModel);

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        secondaryStage.setX(visualBounds.getMaxX()- 25 - scene.getWidth());
        secondaryStage.setY(visualBounds.getMinY()+ 25);

        scene.setOnMousePressed(event -> {
            xOffset=secondaryStage.getX() - event.getScreenX();
            yOffset=secondaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            secondaryStage.setX(event.getScreenX()+ xOffset);
            secondaryStage.setY(event.getScreenY()+ yOffset);

        });

    }
}
