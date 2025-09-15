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
        int getI()
        int getSpots()
        String getUser()
        Date getBookedTime()
        void book(String customerName, Date bookedTime)
        void free()
    }

    class User{
        int id
        String name
        Set&lt;Table&gt bookedTables

        int getId()
        int getName()
        Set&lt;Table&gt getBookedTables()
        Table getBookedTableById(int id)
        void bookTable()
        void freeTable()
    }

    class Restaurant{
        int id
        Set&lt;Table&gt; tables
        String name

        int getId()
        Set&lt;Table&gt getTables()
        Table getTableById(int id)
        String getName()
        void addTable()
    }

    User "1" -- "*" Table : Booking
    Restaurant "1" -- "*" Table : Tables in restaurant
```