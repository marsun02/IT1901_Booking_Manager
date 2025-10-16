```mermaid 

sequenceDiagram
    participant User
    participant UI
    participant controller
    participant bookinglogic
    participant persistance

    User->>UI: Presses "Book table"
    UI->>bookinglogic: Sends booking request
    bookinglogic->>persistance: Read existing bookings
    persistance-->>bookinglogic: Return data
    bookinglogic->>persistance: Save new booking
    persistance-->>controller: Confirm saving
    controller-->>UI: Show confirmation
    UI-->>User: Show