# windsurfingforecast

CONFIG:

Before run application you must obtain your own API key from https://www.weatherbit.io/api/weather-forecast-16-day
and put it in application.ympl 

RUN:

you can run application via IDE and execute WindsurfingforecastApplication in Main package

EXEMPLE USAGE:

Use postman or other tool for REST requests
and send GET request to localhost:8080/api/forecast/spot?date=2023-03-17

or use CURL
curl -X GET \
   'localhost:8080/api/forecast/spot?date=2023-03-17' \
   -H 'content-type: application/json' \
 '
 
 EXEMPLE RESPONSE 
 
 {
	"spotName": "Bridgetown",
	"forecastDTO": {
		"wind_spd": 6.2,
		"temp": 25.5,
		"datetime": "2023-03-17",
		"spotScoring": 44.1
	}
}
