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
##Tools explanation
###Dagger usage
        
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
        ** Uses the mapper to map the Entity from __Domain Layer__
        ** Gets passed a View that has a setPresenter (Contract tells you that), so it calls it on itself.
        ** Creates a __Subscriber__ to listen for the execution of the task that he handles over to the __UseCase__ from the DomainLaer that is also part of the constructor params.
##Tools explanation
###RxJava usage


#Domain Layer:
- Executor contains interfaces for Data model (Thread executor) and UI (PostExecution) to implement
- SingleStory is an abstract class that handles the disposables, same for CompletableStory but for different Observers
- model has the representation of business entities
- repository has the interface of all the methods to communicate with Data Layer (acts as a requirement)

#Data Layer:
