This architecture boilerplate is based on the boilerplate provided by the [awesome guys at Bufferoo](https://github.com/bufferapp/android-clean-architecture-boilerplate) 
I just edited the class names and tweaked a little here and there to learn how they built it and to make it a good starting point for my future projects.
# UI Layer:
##General Info
1. The only module dependent on Android Framework
2. Communicates with the presenter via a Contract (Interface)
##Packages explanation
* Main   
    * UIThread: Implements PostExecutionThread defined in the __Domain Layer__ giving Android Ui Main thread as an executor.
    * MyApplication: Wraps the application using Dagger and injects the activity using the __HasActivityInjector__ Interface.
* Model: View Model for the UI Layer
    * MyEntityViewModel: Entity with fields that you want to in the UI Layer.
* Mapper: Entity mappers to ViewModels
    * MyEntityMapper implements the __Mapper__ interface and maps the MyEntityView to its ViewModel
* MyUseCase: named after the use case for the acrivity
    * Activity: Implements the View part of of the Presentation Layer contract that also brings the BaseView interface specifying that the view has a Presenter.
    In its method it only interacts with the UI and nothing more.
    * Adapter: Extends RecyclerView adapter with the ViewHolder pattern.
* Injection: Package that handles all the things needed for DI
    * ApplicationComponent: interface for Dagger that wraps the Module on this Layer
    * Scopes: defines annotation to wrap Dagger scopes Application or Activity.
    * Module: modules (dagger @Provider s)
        * ApplicationModule: provides alls ort of global access objects (repo, remote, cache, appContext, Preferences)
        * MyUseCaseActivityModule: Provides View and Presenter of the MyUseCase Contract.
        * ActivityBindingModule: binds the activity to its module.
        
#Presentation Layer:
- Provides a BaseView which gives access to the setPresenter method
- BasePresenter provides an Interface for any presenter
- Implementation of Presenter acts with a Contracted view, an UseCase and a mapper to provide the content as requested.
** Presenter uses an RxJava Subscriber as an inner class and pass it to the use case as an executor
- Mapper follows an interface with a method made to map to and from the Domain Layer

#Domain Layer:
- Executor contains interfaces for Data model (Thread executor) and UI (PostExecution) to implement
- SingleUseCase is an abstract class that handles the disposables, same for CompletableUseCase but for different Observers
- model has the representation of business entities
- repository has the interface of all the methods to communicate with Data Layer (acts as a requirement)

#Data Layer:
