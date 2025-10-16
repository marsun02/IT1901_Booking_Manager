package ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainPageController mainPageController;


    // There comes spotbugs from these, but Stage and Scene should not be copied, therefore the spotsbugs remain for now
    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
        this.primaryStage = primaryStage;
        this.mainScene = bookingScene;
    }

    public void setMainPageController(MainPageController controller){
        this.mainPageController = controller;
    }

    
}
