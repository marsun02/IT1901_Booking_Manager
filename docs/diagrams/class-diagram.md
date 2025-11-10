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

    class CustomDateObject {
        String date
        String time
        String restaurantName
        String user
        String bookedName
        String firstName
        String lastName
        int id

        String getDate()
        String getTime()
        String getRestaurantName()
        String getUser()
        String getBookedName()
        String getFirstName()
        String getLastName()
        Integer getId()
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

    User "1" -- "*" CustomDateObject : creates bookings
    Restaurant "1" -- "*" CustomDateObject : bookings for restaurant
    Restaurant "*" --> "1" FileManagement : save/load
    User "*" --> "1" FileManagement : save/load
    CustomDateObject "*" --> "1" FileManagement : save/load
```

**[Back to root README](../../README.md)**