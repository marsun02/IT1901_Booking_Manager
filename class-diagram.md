```mermaid
---
title: Class diagram
---

classDiagram
    class Table{
        boolean isBooked
        int id
        int spots
        User user
        Date bookedTime

        boolean getStatus()
        int getId()
        int getSpots()
        String getUser()
        Date getBookedTime()
        void book(String customerName, Date bookedTime)
        void free()
    }

    class User{
        int id
        String name
        Collection&lt;Table&gt bookedTables
        int phonenumber

        int getId()
        int getName()
        Collection&lt;Table&gt getBookedTables()
        Table getBookedTableById(int id)
        int getPhonenumber
        void bookTable()
        void freeTable()
    }

    class Restaurant{
        int id
        Set&lt;Table&gt; tables
        String name

        int getId()
        Collection&lt;Table&gt getTables()
        Collection&lt;Table&gt getAvailableTables()
        Table getTableById(int id)
        String getName()
        void addTable()
    }

    User "1" -- "*" Table : Booking
    Restaurant "1" -- "*" Table : Tables in restaurant
```