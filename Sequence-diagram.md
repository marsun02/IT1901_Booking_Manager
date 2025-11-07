```mermaid 

sequenceDiagram
    participant User
    participant BookingPageController
    participant ServerCommunication
    participant bookings
    participant id
    participant Filemanagement

    User->>BookingPageController: Chooses restaurant, date, time and fills in name
    BookingPageController->>ServerCommunication: updateId("bookingId")
    ServerCommunication->>id:Get
    id->>Filemanagement: update ids in ids.jsn
    Filemanagement-->id: return new id
    id-->ServerCommunication: return new id
    ServerCommunication-->BookingPageController: Return id
    BookingPageController->>ServerCommunication: saveBooking(CustomDateObject)
    ServerCommunication->>bookings: Post
    bookings->>Filemanagement: saveBookings(CustomDateObject)
    Filemanagement-->bookings: confirm booking
    bookings-->ServerCommunication: 200 ok
    ServerCommunication-->BookingPageController: 200 ok
    BookingPageController-->User: shows confirmed booking
    
```