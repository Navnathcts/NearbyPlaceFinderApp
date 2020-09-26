# NearbyPlaceFinderApp
This is sample app which shows list of nearby places based on type like restaurants, schools, pubs and so on.

MVVM Architecture:

MVVM has mainly the following layers:

> Model
Model represents the data and business logic of the app. One of the recommended implementation strategies of this layer, is to expose its data through observables to be decoupled completely from ViewModel or any other observer/consumer

> ViewModel
ViewModel interacts with model and also prepares observable(s) that can be observed by a View. ViewModel can optionally provide hooks for the view to pass events to the model.
One of the important implementation strategies of this layer is to decouple it from the View, i.e, ViewModel should not be aware about the view who is interacting with.

> View
Finally, the view role in this pattern is to observe (or subscribe to) a ViewModel observable to get data in order to update UI elements accordingly.

Libraries used:

Retrofit :  2.6.0 - Retrofit is a type-safe HTTP client for Android and Java. Retrofit makes it easy to connect to a REST web service by translating the API into Java interfaces. This powerful library makes it easy to consume JSON or XML data which is then parsed into Plain Old Java Objects (POJOs)

Okhttp :    3.14.1' - HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.

navigation : 2.3.0 - Navigation in the app becomes very easy, no need to handle back and forward flow of app. Navigation lib do that for us.

play-services-location: 17.1.0 - To fetch user current location, it is used.
