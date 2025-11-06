## Rest api

### Request formats
The api requests can in this project be separated into two categories: Those who read from the data files to get information, and those who put information to the file. The methods that read use the HTTP GET format. They do not modify the data files in any way. The others use the POST format. They may or may not read data before writing something.

#### Examples:
- GET: GetAllBookings
- POST without reading: SaveAllRestaurants
- POST with reading: DeleteBooking (This request needs to read the collection, before modifying and writing it back)

### Controllers
The data storage is divided into four unique data files containing different information in json format: bookings, ids, restaurants, users. Each data file has its own controller containing the api calls that read/modify that specific file.

### Included requests
Before implementing the api calls, we made thorough assesment on which calls needed to be included. We concluded with calls to get the entire data contents for all files except ids.json. The POST requests are a bit varied. For the restaurant file, we have a POST that overwrites the entire file with new data. In Bookings and users, the POST requests first read the data, then add the new data and writes the entire collection to the file. The id file only has one api call which acts both as a read and write method. It updates the id by incrementing it by one and returning the newly incremented id.

### Implementation
The server module includes four main parts: The app, the controllers, the data files and the file management logic. The controllers include all the api calls, which again call all the methods in the FileManagement.java class. The file managements modify the data files. All this is wrapped in a spring boot app that needs to be run separately.

When implementing this, we decided to keep the old file management all the way until all the calls were done. When the calls were done, we made a ServerCommunication.java class to handle the calling of api calls. This is located in the core module. Once the api was ready, the persistence module was removed and all its method calls was replaced with api calls (ServerCommunication methods).

[Root README](../../README.md)