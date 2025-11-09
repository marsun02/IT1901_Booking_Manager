```mermaid
---
title: Class diagram
---

classDiagram
    class User{
        int id
        String name
        String email
        String password
        String phoneNumber

        int getId()
        String getName()
        String getEmail()
        String getPhonenumber()
        boolean checkNamePassword(String name, String password)
    }

    class Restaurant{
        int id
        String name
        int amountOfTables

        String getName()
        int getId()
        int getAmountOfTables()
    }

    class FileManagement{

        saveRestaurants(File, Collection~Restaurant~)
        loadRestaurants(File)
        saveBooking(File, CustomDateObject)
        deleteBooking(File, CustomDateObject)
        loadBookingsForSpecificDate(File, date, restaurantName)
        loadBookingsForSpecificUser(File, user)
        loadAllBookings(File)
        loadSpecificBooking(File, id)
        loadUsers(File)
        saveUser(File, User)
        updateId(File, idType)
    }

    User "1" -- "*" Table : Booking
    Restaurant "1" -- "*" Table : Tables in restaurant
    Restaurant "*" <-- "1" FileManagement : Save/Load
```

**[Back to root README](README.md)**