# Project Title; News-App

Description
This app displays a list of recent news around the world
from the Guardian Free API.

What i've learnt and implemented in this project:


Connecting to an app to the internet using an API (here i used the Guardian API).
Reading and parsing JSON data.
Network and server connection.
HTTP Request and JSON Parsing.
Async Loader and callback methods.
Preference Fragments.
Using progress bar and listview empty for better User interface
OnItemClickListener and OnSharedPreferenceChangedListener
Customizing ListViews,using ArrayLists & ArrayAdapters.
An News.java class was created in order to parse title, sectionName, date, url and authour in order to implement the functionality of the code. This states are stored in each instance of an article object which are added to an ArrayList of type Article.

The Loader implemented was useful in order to respond to screen rotation or other phases of the Activity Lifecycle that could restart the method onCreate(), so that when the loader already exists, the infomation will keep on the screen.
