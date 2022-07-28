# Programmierung 2 (BTX8042) 22
## LoR Companion App

This app is meant as a Companion for the CardGame LoR ([Legends of Runeterra](https://playruneterra.com)).  
It uses the official Json files as a Base to work with. The Data-Model is built around those Json files.  


###Features:
1. Display All Cards in a Table
   1. Edit the Cards
2. Display All Cards as Images in a Gallery
   1. Search the Cards by Name
   2. Search the Cards by intelligent search (description fields and more)
   3. Open Full-Sized Images by middle-Clicking it
      1. See the Associated Cards in that Window
   4. Create a LoR-Deck
      1. Import a [Deck](https://leagueoflegends.fandom.com/wiki/Deck_(Legends_of_Runeterra)) (not all Game Rules are followed)
      2. Export a DeckCode that can be used in the Game
3. Create your Own Cards with Custom Images and save them

###How to use

-If you haven't downloaded the Images yet
--> Run the program Main Class of the CardService class  
-If you have downloaded them and they are at the right spot you are good to go  
The Main way to use this Companion is with the JavaFX GUI App. To run it, execute App (ui.gui.App) Class's Main Method 
And wait for a long time :D (2+GB of images have to be loaded into Ram)



###Todo-List

- [x] Build Basic and functioning Prototype
- [ ] Implement all Fields that are available in the official Json files
- [ ] Implement all Game Logic concerning Card Creation/Mutation and Deck Creation
- [ ] Make the overall look more modern
- [ ] Make the Image-Files and ImageViews always persistent with the Card Objects (DataBase needed)

###UML Diagramm stark vereinfacht nur als Grobe Übersicht
![UML](src/main/resources/Card_Project_Simple.png)