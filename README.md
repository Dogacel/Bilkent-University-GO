# Bilkent University GO

## Project Name: BuGO

---

### Group: g1E

---

### Members: Doğaç Eldenk, Mehmet Ata Yurtsever, Işın Su Ecevit, Adile Büşra Ünver, Faruk Balcı, Cem Cebeci, Can Cebeci

***

### Project Description: 

Every year, over two thousand students are struggling to find their way through the campus and classrooms. With the slightly confusing information of so many buildings and paths, it might be exhausting to discover the campus all by yourself. To help people during this trickish journey, project BUGO (Bilkent University Go), an Android Application is designed to provide the users with many convenient features.

----

### Contributions to project:

#### Doğaç Eldenk:
    
* Implementation of Kudan Augmented Reality Software Development Toolkit
* Implementation of Google Play Location Services
* Connecting the Augmented Reality Toolkit with Location Services
* Processing sensor values for Augmented Reality implementation
* Creating artwork for Augmented Reality markers
* Implementing handlers, managers and tools for GPS based Augmented Reality
* Making connections between activites and transactions
* Fixing bugs on layout and maps for helping other team members

#### Mehmet Ata Yurtsever:
* Implementation of Openlayers Map libraries
* Mapping of Bilkent Campus buildings
* Managing building information
* Combining location services with map in order to show the person's location on the map
* Managing geojson data of the Bilkent Campus buildings

#### Işın Su Ecevit:
* Helping on making Main Menu activity
* Creating and styling the activities
* Improving performance on activities and fixing bugs
* Creating icons and finding pictures for building informations
* Helping the creation of the map and fixing mistakes.

#### Adile Büşra Ünver:
* Implementing the map on the MapActivity
* Adding building polygons to geojson raw data
* Styling the map and making fixes on the map activity
* Making connections with Android and Map

#### Faruk Balcı:
* Making the permissions on the ARActivity
* Adding node handler for transfering information between geojson and ARActivity

#### Cem Cebeci:
* Implementing menu activities
* Making buildling information menu templates
* Making building list activity templates
* Create connector functions for transaction between activities
* Create constants for handling building info

#### Can Cebeci:
* Implementing main menu activity
* Implementing ARActivity template
* Implementing BuildingInfo activity template
* Implementing BuildingList activity
* Organising activites transactions
* Organise constants for building info

  
### Organisation Of The Code: 

The project is divided into couple packages. Those are:

+ <b>activities</b>: Contains all activities for app's UI
+ <b>gps</b>: Contains all classes and sub-packages for handling Augmented Reality, Location Manager and Information Handling tools / handlers
+ <b>sensor</b>: Contains classes for sensors used in project

Other than that, there are 3 classes in project's main package. 

- <b>App</b>: App class is the app's default class. It runs when the application is started. First, It dumps data from geojson and initializes the GPSNodes for ARActivity.
- <b>Constant</b>: Contains all constant and infortmation about both App and Buildings
- <b>ContainerManager</b>: This class has referances to objects that are used by different parts of the project. This prevents re-initialization of some objects and helps carrying data around views without any extra parameters. Objects are stored as static instances.

### Packages:
- <b>gps</b>:
    - <b>ar</b>: 
        - <b>handlers</b>: Has classes for handling building infortmation and GPSWorld objects.
        - <b>tools</b>: Has different tools for couple ARview functions. Has tools for converting data types.
        - <b>units</b>: Has units for carrying information and showing GPS Nodes on the world. Also has a manager inside manages the nodes.
    - <b>information</b>: NodeARInformation for gathering building info from geojson
- <b>location</b>: Has location manager and listener interface.
- <b>sensor</b>: Has tools for storing sensor data and measuring sensor data, especially compass.
- <b>activities</b>: Has activities that are shown on the UI.
                
#### Note:

The gps.ar.* classes can be used to create your own GPS based AR applications. For further info please checkout the [javadoc](/docs). Every class is carefully commented.

### Tools:
* Android Studio used as an Integrated Development Enviroment
* KudanAR as a Augmented Reality Development Toolkit
* DSensor Library for handling sensor data
* Openlayers for rendering map
* Android 4.0+ recommended, targeted API level: 26
* Java 1.7_0+ is recommended for compiling the project

##### Just open the folder containing code with Android Studio then it will automatically load the project. For debugging, logcat has couple tags, NODE_INFO, GPS_DEBUG, COMPASS, etc. Check the source code for more information.

