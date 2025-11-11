# Approach:
- Modularization: Like mentioned in the initial interview, I've emphasized modularization, so I thought I'd do it for this project. I have 2 main modules and 1 support module. `navigation` handles the navigation across the app and `planets` handles the planet screens. `dispatchers` is a small module to provide an IO dispatcher in constructors which can then be replaced by a test dispatcher for unit tests.
- Clean Architecture: Used MVVM and the public, impl, wiring sub modules separate the public API from the implementation.
- Dependency Injection: Used Hilt for dependency injection.
- UI: Per the guidelines, I've used Jetpack Compose for the List screen and a fragment for the Detail screen.

---

# Trade offs
- Complexity: The initial modularization introduces boilerplate and complexity for a small project, which can be over kill. I've included a `build-logic` with preset conventions to reduce the boilerplate, but it was more work that had to be done initially, but it does set a good foundation for future/other projects. 
- Learning Curve: New developers might find it difficult to navigate the project and understand the architecture as it stands as the `:app` module is pretty barebones and the screens/navigation are in separate modules. I do believe this pattern is easily learned and can set good habits once familiarized with it.