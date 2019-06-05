# Movie Collection Application
As part of my Computer Science Degree, one of my assignments was to design and build a Java SwingUI app to create, store and filter a series of movies. The scope of this task was restricted in order to introduce SwingUI functionality at a beginner level and teach the MVC architecture.

### The Design Brief
* A movie consists of a title, director, genre, rating (one to five, inclusive), a release year (upcoming or in the past) and a comma-separated list of cast members. These are mandatory attributes when movies are added.
* When closing the movie collection, it should be saved to a data file that reads restores the collection next time the application opens.
* Users want to be able to filter through their collection by various means, including basic searches and more complex filtering. This should encompass but not be limited to selective date/rating range filtering and sorting.
* Movies can exist multiple times and the user should be warned, if this is the case, when they add it to the collection.
* Users must be able to remove movies from the collection.
* Must be developed using Java in the [Eclipse IDE](https://www.eclipse.org).

## App Overview

#### Main View
The *Main View* is the very first view presented to the user. It shows a collection of films. Below the list, we have the ability to add a movie based on a set of metadata. Off to the right, movies can be filtered based on their attributes. Lastly, a generic search field at the top allows us to quickly search through our collection.

![Main View](/screenshots/main-view.png)

#### Filtering / Searching
We can filter our collection based on a simple search or a specific set of criteria.

Quick Search | Multi-criteria Filter
:-------------------------:|:-------------------------:
![Quick Filter](/screenshots/quick-filter.png)  |  ![Criteria Filter](/screenshots/criteria-filter.png)

#### Saving and Loading Collection
Upon terminating the application, it's collection is saved into a *moviecollection.bin* data file. This file is read back into the application upon starting, in order to restore the movie collection.

![Data File](/screenshots/data-file.png)

### See something that can be improved?
While this is not an active project of mine, I would love to hear from you. Feel free to submit a Pull Request if you can improve this repository or open an issue should you encounter a bug. 🐞
