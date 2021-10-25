#hotel-room-booking-services
This Repository contains the Hotel Room Booking Service source code

The aplication is hosted in Heroku Cloud. below are the endpoints,

End Points :

host URL : https://altir-hotel-booking-app.herokuapp.com

for Admin operations use : https://altir-hotel-booking-app.herokuapp.com/altir/admin/**

for User operations use : https://altir-hotel-booking-app.herokuapp.com/altir/user/**

You can find all the endpoints in Swagger-UI : https://altir-hotel-booking-app.herokuapp.com/swagger-ui/

Admin Endpoints
1. /altir/admin/rooms - GET
Result : View All Rooms(both available and not available)
2. /altir/admin/rooms - POST
Result : Admin can Insert new Room Details
3. /altir/admin/rooms/{hotelName} - GET
Result : Admin can View all Rooms of a Hotel
4. /altir/admin/rooms/{hotelName} - DELETE
Result : Admin can Delete Rooms of a Hotel
5. /altir/admin/rooms/{hotelName}/{roomNumber} - PUT
Result : Admin can Update Room details of a Hotel using hotel name and Room
Number
6. /altir/admin/rooms/{id} - PUT
Result : Admin can Update Room details of a using Room ID
7. /altir/admin/rooms/available - GET
Result : Admin can view all Available rooms
8. /altir/admin/rooms/booked - GET
Result : Admin can view all Booked rooms
9. /altir/admin/rooms/totalrevenue - GET
Result : Admin can view the Total Revenue
10. /altir/admin/users - GET
Result : Admin can view all the Users who booked hotel rooms
11. /altir/admin/users/{userName}/booking/price - GET
Result : Admin can view total price of Booked room/s of a User using username
Note : Only Admin(Admin credentials) can access the above links


User Endpoints
1. /altir/users/{userName}/rooms/{hotelName}/{roomNumber}?date=1&date=2&
date=3 - POST
Result : User can Book a room for Multiple Dates or Book for any Random date
Note: For multiple dates, there will many date parameters in the request URI and
for random or single date there will be only one date parameter
2. /altir/users/{userName}/rooms/{id} - POST
Result : User can Book a room for by using Room ID(Primary key of room from DB)
and date will be added as current date
3. /altir/users/rooms - GET
Result : User can view all the Rooms (both available and not available rooms)
4. /altir/users/rooms/available - GET
Result : User can view all the Available Rooms (only available rooms)
5. /altir/users/signup - GET
Result : User can Register or Sign Up