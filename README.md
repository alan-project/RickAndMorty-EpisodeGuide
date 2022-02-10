<h1 align="center">Rick and Morty Episode Guide</h1>




<p align="center">  
This application is based on modern Android tech-stacks and MVVM with Clean architecture.
</p></br>
<p align="center"> 
<img src="/preview/main_image.png"/>
  </p>

## Rick and Morty

Rick and Morty is an American adult animated science fiction sitcom created by Justin Roiland and Dan Harmon for Cartoon Network's nighttime programming block, Adult Swim. 

The series follows the misadventures of cynical mad scientist Rick Sanchez and his good-hearted, but fretful grandson Morty Smith, who split their time between domestic life and interdimensional adventures.
</br></br></br></br>

## Screen Shot
<p align="center">  
<img src="/preview/screen_shot.jpg"/>
</p>

## Download
Go to the [Google Play](https://play.google.com/store/apps/details?id=net.alanproject.rickandmorty) to download the latest APK.

<img src="/preview/recording.gif" align="right" width="30%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based, [RxJava](https://github.com/ReactiveX/RxJava) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - DataBinding - to easily observe data for changes
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct the database using the abstract layer.
- Architecture
  - Clean Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.

## Open API

<img src="/preview/api.png" width="30%">

Rick and Morty is running withthe [rickandmortyapi.com](https://rickandmortyapi.com) for constructing RESTful API.<br>
The Rick and Morty API is a REST(ish) and GraphQL API based on the television show Rick and Morty. You will have access to about hundreds of characters, images, locations and episodes. The Rick and Morty API is filled with canonical information as seen on the TV show.
