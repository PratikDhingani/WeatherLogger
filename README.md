# WeatherLogger

# Description

Create an Android application to save weather conditions for your current location.

**API** : https://api.openweathermap.org/

1) Followed Application Architecture : **MVVM** as a design pattern.
   - **View** : contains view part like Activity, Fragment, Adapter.
   - **ViewModel** : to fetch data from model tasks and update UI through LiveData.
   - **Model** : contains common classes, repository, business logic classes.
3) Used Jetpack component like **LiveData, Databinding, Navigation component architecture, Room Database**.
4) There is one activity and two fragments as recommended approach by google for navigation component architecture (fragment to fragment transition).
5) Used room database to store local weather data.
6) Added Unit test and UI test using JUnit, Espresso, Truth library.
   - **JUnit** : To test business logic inside model. Here we have not unit tested for View and ViewModel because there is no logic inside it and getting tested under UI testing through Espresso.
   - **Espresso** : Espresso helps to launch Activity & Fragment to test UI part. It also provides the check API and matcher e.g. #isCompletelyDisplayed, etc.
   - **Truth** : To assert values and objects.
7) **LottieAnimation** : library for animation.
8) **Retrofit** : To fetch the data from Web Api.
9) Frequently feating data based on locaion update.

Please find apk at https://github.com/PratikDhingani/WeatherLogger/WeatherLogger.apk
**Screenshots** : 
