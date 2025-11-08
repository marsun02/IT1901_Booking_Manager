```mermaid
    flowchart TD

    %% packages

    subgraph UI
        App
        MainPageController
        BookingPageController
        LoginPageController
    end    


    
    subgraph Core
        ServerCommunication
        ControllerHelpFunctions
        RestaurantInitialization
    end   

    
    subgraph Model
        User
        Restaurant
        CustomDateObject
    end 


    subgraph Server_Controller
        BookingController
        RestaurantController
        UserController
        IdController
    end

    subgraph Server_Persistece
        FileManagement
    end
```