# Kainderya
Your local silogan in town.

Programmers:
- Gianna Katrin Carreon
- Aaron Dei Jatayna
- Venice Jonah Juarez
- Lorraine Quezada

<br>S.Y. 2024 - 2025
<br>BS Computer Science
<br>Cebu Institute of Technology - University


# DESCRIPTION
KAINDERYA is a 2D pixel cooking game where players run and manage a fast-paced Filipino eatery! Inspired by popular games like 'Ready-Set-Cook' and 'Overcooked', players are challenged to cook, assemble, and serve classic dishes from local karinderya or tapsilogan under the pressure of time and customer demands. Whether it's serving silog meals or handling a busy karinderya crowd. 

(Multiplayer feature coming soon.)

# MECHANICS
SINGLE PLAYER:
     <br> 1. Acknowledge the customers' orders
     <br> 2. Get and complete their orders repeatedly
     <br> 3. Customers eat a lot so watch out for their consecutive orders!

# GAME OBJECTIVE
Complete orders quickly within a fixed duration to achieve high scores.

# DESIGN PATTERNS USED

**FACTORY**<br>
The Factory pattern is used to create objects without specifying the exact class of object that will be created. This allows for flexible object creation. In our game, it is used to create different types of animations (AnimationFactory), game assets (like NPCs and items through AssetSetter), and UI elements (UIFactory) based on the current game state.

Classes: *AnimationFactory, AssetSetter, UIFactory*

**ABSTRACT FACTORY** <br>
The Abstract Factory pattern allows us to produce related objects (e.g., game assets like NPCs, items, or workstations) without specifying their concrete classes. In our game, this pattern is used in the creation of different asset types such as SuperObject, WorkStation, and Item using the AssetSetter class, and also for managing various UI components in the UIFactory class.

Classes: *Asset, SuperObject, WorkStation, Item, UIFactory*

**SINGLETON** <br>
The Singleton pattern ensures that only one instance of certain classes exists throughout the game. This is important for components like GamePanel, EventHandler, and Sound, where only one instance should control the game state, handle events, and manage the audio. For example, GamePanel is responsible for the game loop, and having multiple instances could lead to inconsistent behavior.

Classes: *GamePanel, EventHandler, KeyBindings, Sound*

**BUILDER**<br>
The Builder pattern is used to construct complex objects step by step. In the game, AssetSetter acts as a builder for creating game objects like workstations, items, and NPCs. This pattern simplifies the process of placing these objects at specific locations on the game map while maintaining flexibility.

Class: *AssetSetter*
