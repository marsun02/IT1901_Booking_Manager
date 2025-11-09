```mermaid
---
title: Package diagram
---
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

    subgraph Server_Persistence
        FileManagement
    end


    %% Dependencies
    UI --> Core
    UI --> Model

    Core --> Model
    Core --> Server_Controller

    Server_Controller --> Model
    Server_Controller --> Server_Persistence

    Server_Persistence --> Model
```

**[Back to root README](../../README.md)**