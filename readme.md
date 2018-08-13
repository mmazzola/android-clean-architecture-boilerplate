This architecture boilerplate is based on the boilerplate provided by the [awesome guys at Bufferoo](https://github.com/bufferapp/android-clean-architecture-boilerplate) 
I just edited the class names and tweaked a little here and there to learn how they built it and to make it a good starting point for my future projects.
# UI Layer:
1. The only module dependent on Android Framework
2. Communicates with the presenter via a Contract (Interface)
##Packages explanation
* Main   
    * UIThread: Implements PostExecutionThread defined in the __Domain Layer__ giving Android Ui Main thread as an executor.
    * MyApplication: Wraps the application using Dagger and injects the activity using the __HasActivityInjector__ Interface.
* Model: View Model for the UI Layer
    * MyEntityViewModel: Entity with fields that you want to in the UI Layer.
* Mapper: View mappers to ViewModels
    * MyEntityMapper implements the __Mapper__ interface and maps the MyEntityView to its ViewModel
* MyStory: named after the use case for the activity
    * Activity: Implements the View part of of the Presentation Layer contract that also brings the BaseView interface specifying that the view has a Presenter.
    In its method it only interacts with the UI and nothing more.
    * Adapter: Extends RecyclerView adapter with the ViewHolder pattern.
* Injection: Package that handles all the things needed for DI
    * ApplicationComponent: interface for Dagger that wraps the Module on this Layer
    * Scopes: defines annotation to wrap Dagger scopes Application or Activity.
    * Module: modules (dagger @Provider s)
        * ApplicationModule: provides alls ort of global access objects (repo, remote, cache, appContext, Preferences)
        * MyStoryActivityModule: Provides View and Presenter of the MyStory Contract.
        * ActivityBindingModule: binds the activity to its module.
##Dagger usage
        
#Presentation Layer:
1.Provides BaseView interface with setPresenter method and BasePresenter as an Interface for any presenter.
2. Acts as if it knows nothing about the UI.
##Packages explanation
* Model: contains the views with the interpretation of the presentation layer
* Mapper: maps Entity into View
    * MyEntityMapper: Maps MyEntity into its view representation.
* MyStory:
    * MyStoryContract: states the features that the view and the presenter should provide.
    * MyStoryPresenter: implements the Presenter part of the contract.
        * Uses the mapper to map the Entity from __Domain Layer__
        * Gets passed a View that has a setPresenter (Contract tells you that), so it calls it on itself.
        * Creates a __Subscriber__ to listen for the execution of the task that he handles over to the __UseCase__ from the DomainLaer that is also part of the constructor params.
##RxJava usage

#Domain Layer:
1. Has the responsibility to contain the UseCase logic to get the data from __Data Layer__ and pass it onto the __Presentation Layer__
2. Since its the central layer of the architecture it defines the Entity class without mapping it from anywhere.
3. The Repository interface acts as a contract for external layers to the __Data Layer__
##Packages explanation
* Executor: contains interfaces for Data model (Thread executor) and UI (PostExecutionThread) to implement
    * PostExecutionThread: abstraction to change execution context from any thread to any thread.
* Interactor: 
    * Single/CompletableUseCase: abstract class that handles task execution receiving a Disposable (from __Presentation Layer__)
    * MyStory:
        * DoSomething: extends one of the UseCase abstract class and implements the __buildUseCaseObservable__ method to "do its job".
* Model: representation of Entity got from an external layer
* Repository:  Interface to be implemented by the __Data Layer__ that defines how it can pass data to/from the Domain Layer.
##RxJava usage

#Data Layer:
1. Acts as an access point to external data layers to fetch data from multiple source (cache, network...)
##Packages explanation
* Main: Contains an implementation of the Repository that uses a DataFactory and a Mapper
* Executor: Implements ThreadExecutor from the __Presentation Layer__
* Model: contains the MyEntityEntity, representation of the entity got from an external data source (not layer).
* Mapper: interface that defines methods to go to/from and its MyEntity implementation
* Repository: interfaces to be implemented in source package
    * MyEntityDataStore: defines operations of the DataStore (CRUD)
    * MyEntityCache: defines the same operations plus cache related ones
    * MyEntityRemote: defines operations possible on the remote DataSource
* Source: Cache/RemoteDataStore both implements MyEntityDataStore they are handled by the DataStoreFactory depending on the status of the cache.

#Remote Layer:
1. Handles communication with remote sources
2. Uses a SingletonFactory to create instances of MyEntityService using Retrofit.
##Retrofit Usage

#Cache Layer:
1. Handles communication with the local database, used to cache data

