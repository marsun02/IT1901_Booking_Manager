<!-- This text is copied from release 2 and modified with updated info to fit this third release -->

## Persistence
Our application uses JSON to store restaurant data, which again uses the Jackson library. This format makes everything human-readable and easy to maintain, which also makes it suitable for storing structured user data like restaurant details and bookings. The saving/loading functionality is implemented by a [rest api](rest-api.md), and all the data files are located in a module named server.

The benefit of using JSON over other file saving methods, is that JSON allows us to store the objects directly in the file, and load them in directly. This makes it easier to access, as they do not have to be modified when being used. This allows us to avoid any extra methods or workarounds that would otherwise be needed.

We have four data files used to store all the information needed to run the app:
- Users: This file stores all the users with the corresponding info such as passwords, mail, etc. This info is mainly used to enable the user to log in with an account before booking tables.
- Restaurants: This file stores the information on how many free/taken tables are in each restaurant. This info decides the amount of time slots able to be booked in that restaurant.
- Bookings: This file keeps track of all the bookings made, an which user and restaurant it is linked to. Keeping track of this info is essential to avoid double bookings and enable the users to see their own bookings.
- Ids: This is a small data file used to store the latest id used when making bookings. This ensures that all the ids assigned to bookings are unique. The same goes for user ids(stored in the same file).

All the data files are structured to contain only one object, in most cases a Collection of object. This allows easier manipulation as only saving/loading one singular collection is necessary instead of loading/storing multiple objects at once.

**[Back to root README](../../README.md)**