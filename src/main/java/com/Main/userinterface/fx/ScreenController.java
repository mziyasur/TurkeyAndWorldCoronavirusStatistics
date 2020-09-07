package com.Main.userinterface.fx;

import com.Main.datafetch.DataService;
import com.Main.datafetch.model.CountryData;
import com.Main.datafetch.model.CovidDataModel;
import com.Main.datafetch.model.GlobalData;
import com.Main.settings.SettingModel;
import com.Main.settings.SettingService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Window;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenController implements Initializable {

    private ScheduledExecutorService executorService;

    private SettingModel settingModel;

    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text textGlobalReport;
    @FXML
    public Text textCountryReport;
    @FXML
    public Text textCountryCode;

    public static void main(String[] args) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeScheduler();
        initializeContextMenu();
    }

    private void initializeScheduler() {
        try {
            settingModel = new SettingService().getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::loadData, 0, settingModel.getRefreshIntervalInSeconds(), TimeUnit.SECONDS);
    }

    private void loadData() {

        System.out.println("Refreshing...");
        DataService dataService = new DataService();
        CovidDataModel covidDataModel = dataService.getData("Turkey");
        InflateData(covidDataModel);

        Platform.runLater(() -> {
            InflateData(covidDataModel);
        });

    }

    private void InflateData(CovidDataModel covidDataModel) {
        GlobalData globalData = covidDataModel.getGlobalData();
        textGlobalReport.setText(getFormattedData(globalData.getCases(), globalData.getRecovered(), globalData.getDeaths()));
        CountryData countryData = covidDataModel.getCountryData();
        textCountryReport.setText(getFormattedData(countryData.getCases(), countryData.getRecovered(), countryData.getDeaths()));

        readjustStage(textCountryCode.getScene().getWindow());
    }

    private String getFormattedData(long totalCases, long recoveredCases, long totalDeaths) {
        return String.format("Vaka: %-8d | Iyilesen: %-6d | Olum: %-6d", totalCases, recoveredCases, totalDeaths);
    }

    private void readjustStage(Window stage){
        stage.sizeToScene();
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(visualBounds.getMaxX()- 25 - textCountryCode.getScene().getWidth());
        stage.setY(visualBounds.getMinY()+ 25);
    }

    private void initializeContextMenu(){
        MenuItem exitItem = new MenuItem("Cikis");
        exitItem.setOnAction(event -> {
            System.exit(0);
        });

        MenuItem refreshItem = new MenuItem("Yenile");
        refreshItem.setOnAction(event -> {
            executorService.schedule(this::loadData,0,TimeUnit.SECONDS);
        });

        final ContextMenu contextMenu = new ContextMenu(exitItem,refreshItem);
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED,event -> {
            if(event.isSecondaryButtonDown()){
                contextMenu.show(rootPane,event.getScreenX(),event.getScreenY());
            }else{
                if(contextMenu.isShowing()){
                    contextMenu.hide();
                }
            }
        });
    }

}
