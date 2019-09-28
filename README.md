# SpaceX Demo Project

[Click to download demo video](https://raw.githubusercontent.com/Edward-Inaction/SpaceXDemo/master/art/demo.webm)

## Design

The app is built with Kotlin base on MVVM and Clean architecture.

There are 4 layers: Model, UserCase, ViewModel and UI, they are connected by a dependency injection library called Koin.

Model presents all entities.

UserCase presents all features which are fetching data in this app.

ViewModel connects user case with UI interaction, it receives user actions and presents result  from user cases by generate states which will be bound to View by data binding.

View presents actual UI to users.


## Dependency

### Language

- Kotlin
- KTX

### Architecture

- Lifecycle
- LiveData
- Databinding
- Koin

### View

- ConstraitLayout
- RecyclerView
- SwipeRefreshYaout
- CardView

### Network

- Retrofit

