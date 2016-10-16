# SkylarkTest
This is a Skylark test developed using Public Skylark API.
The idea is to create simple app driven by an instance of the Skylark API filled with sample data.
Skylark is a video platform that enables our clients to curate their video content.
Skylark has API endpoints for Sets and TV Episodes.
The purpose of this application is to show how to create a presentable Application using this API.

#API Documentation
For this Test we used Two different API.
Sets API: Allow to get all the Sets from Server and show to the user.
Episode API: Allow to get all information about a single Episode.

#Architecture
For this test I used MVP + Clean Architecture.
You can find here More information about this type of architecture: https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029#.a74dia9w5

1. MVP: Model View Presenter pattern from the base sample.

2. Domain: Holds all business logic. The domain layer starts with classes named use cases or interactors used by the application presenters. These use cases represent all the possible actions a developer can perform from the presentation layer.

3. Repository: Repository pattern from the base sample.

##Testability
With this approach, all domain code is tested with unit tests.

##Maintainability
Ease to maintain and add feature in future.

#Organization
This project is organized by Application features.
This approach uses packages to reflect the feature set.
- com.maninder.skylarktest.data: Is a subpackage for all the Remote and Local repository
- com.maninder.skylarktest.episode: Contains classes related to Episode Layout.
- com.maninder.skylarktest.setcontents: Contains classes related to Set Contents List. Contain also subpackage related to this features.
- com.maninder.skylarktest.threading: Is a subpackage for all background stuff.

#Dependency
Retrofit 2.0: retrieve and show data from Server.

RxJava: Get Async Server request.

ButterKnife: Bind View in simple way.

Picasso: Show Image into the Application.

Mockito (Unit test): To test the Application.

#Repository
Clone a remote repository

1. On GitHub, navigate to your fork of the Spoon-Knife repository.
2. Under your repository name, click Clone or download.
3. In the Clone with HTTPs section, copy the clone URL for the repository.
4. Open Terminal.
5. Type git clone, and then paste the URL you copied in Step 2.
$ git clone https://github.com/ManinderBan/SkylarkTest.git folder-name
6. Press Enter. Your local clone will be created.

