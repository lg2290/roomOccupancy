# Room Occupancy API

Sample API using Spring Boot

## API Details

The API contains a single endpoint: /v1/rooms/optimizeOccupancy

It requires three parameters:

* freePremiumRooms - Number, greater or equal to zero. Represents the number of free Rooms for the category Premium
* freeEconomyRooms - Number, greater or equal to zero. Represents the number of free Rooms for the category Economy
* potentialGuests - Array of numbers, represents the value that guests are willing to pay for a night

The API makes the division of guests across the free rooms, in a way that:
* Clients that offer 100 or more for a night are allocated only to Premium Rooms
* Clients willing to pay less than 100 may get Premium rooms, if
	* All the Economy Rooms are allocated
	* There are free Premium Rooms after all the clients paying 100 or more got rooms

### Example
Request:

```
/v1/rooms/optimizeOccupancy?freePremiumRooms=2&freeEconomyRooms=1&potentialGuests=120&potentialGuests=40&potentialGuests=70&potentialGuests=99
```

Response:

```
{
	"requestTimestamp": "2018-11-21T00:22:48.382Z",
	"result": {
		"premiumOccupancy": {
			"numberOfOccupiedRooms":2,
			"generatedIncome":219
		},
		"economyOccupancy": {
			"numberOfOccupiedRooms":1,
			"generatedIncome":70
		}
	}
}
```


## Try it now!
Download STS IDE - https://spring.io/tools/sts/all

### Clone Repo and Import It
Clone the repository to your local machine using git, and then import it to STS
```
git clone https://github.com/lg2290/roomOccupancy.git
```

### Build
Run the following command to download the dependencies build the project

```
mvn clean install
```

### Test
To run the tests, use the following command

```
mvn test
```

### Run

```
mvn spring-boot:run
```