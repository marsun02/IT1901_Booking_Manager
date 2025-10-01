package gr2515.ui;

import gr2515.App;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookingPageController {

    private App app;
    private Stage primaryStage;
    private Scene bookingScene;
    private Scene mainScene;

    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
      this.app = app;
      this.primaryStage = primaryStage;
      this.bookingScene = bookingScene;
      this.mainScene = mainScene;
    }






    public void returnToMainPage(){
        primaryStage.setScene(mainScene);
    }

}
