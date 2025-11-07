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
    ServerCommunication->>id: GET /api/ids?idType=bookingId
    id->>Filemanagement: update ids in ids.jsn
    Filemanagement-->id: return new id
    id-->ServerCommunication: return new id
    ServerCommunication-->BookingPageController: Return id
    BookingPageController->>ServerCommunication: saveBooking(CustomDateObject)
    ServerCommunication->>bookings: POST /api/bookings/save
    bookings->>Filemanagement: saveBookings(CustomDateObject)
    Filemanagement-->bookings: confirm booking
    bookings-->ServerCommunication: 200 OK
    ServerCommunication-->BookingPageController: 200 OK
    BookingPageController-->User: Shows confirmed booking
    
```