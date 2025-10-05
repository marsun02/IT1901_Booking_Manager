## Persistence
Our application uses JSON to store restaurant data, which again uses the Jackson library. This format makes everything human-readable and easy to maintain, which also makes it suitable for storing structured user data like restaurant details and tables. Our app mainly has two functions when regarding file storing and loading.

The benefit of using JSON over other file saving methods, is that JSON allows us to store the objects directly in the file, and load them in directly. This makes it easier to access, as they do not have to be modified when being used. This allows us to avoid any extra functions or workarounds that would otherwise be needed.

Our JSON file mainly saves the restaurant objects and their contents. We have chosen to implement a basic reset functionality, which clears the file and re-inserts new restaurant objects if the file is corrupt. We run this function as a failsafe when initializing the app.