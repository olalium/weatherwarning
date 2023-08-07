# weatherwarning (Lightning ⚡⚡⚡)
App to get information about lightning using data from MET Norway (Frost API) and Amazon SNS. Only works in areas Frost API has coverage, basically Scandinavia.
When properly configured the app will send SMS messages with information about lightning near your location.
## Example SMS message
```
time: 15:01:39.984645376
distance: 38.0km
current: 3kamp
type: air to ground
gmaps: https://www.google.com/maps/search/?api=1&query=67.8017,24.5751
```

## Prerequisites
Java 17

## install, compile and build jar
1. to install dependencies, compile and build jar run ```./mvnw install```
2. then run the outputted jar with ```java -jar ```

## Environmental variables
You need to pass the following environmental variables.

| Environmental variable | description                                                              |
|------------------------|--------------------------------------------------------------------------|
| met-client-id          | client id for the frost.met.no api https://frost.met.no/howto.html       |
| met-client-secret      | client secret for the frost.met.no api https://frost.met.no/howto.html   |
| location-lat           | latitude of the area you want notifications for                          |
| location-long          | longitude of the area you want notifications for                         |
| max-distance-km        | if lightning is observed within this amount of kms, notify the user      |
| phone-number           | number of the user, add regional code, ie +47 for Norway                 |
| aws-region             | aws region of your sns service                                           |
| aws-access-key         | aws access key for your aws IAM user                                     |
| aws-secret-key         | aws secret key for your aws IAM user                                     |

