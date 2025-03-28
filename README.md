# Griper (Tweezer) UPCap

In this repo we have two main parts. The first one, the griper simulator, that simulates a real griper that should be connected to a UR cobot, that acts as a server, and second, the griper URCap, with the name Tweezer, that acts as a client.

### Structure

For the structure of the URCap we have implemented 3 separated parts that are related to each other. The Communicator class, that deals with all the socket management. The Toolbar, that we explain in the bottom. Finally, the Program node, for the moment, we are implementing this last part.

![URCap structure](https://github.com/user-attachments/assets/e0d7b4c0-0b3f-4cb8-ac96-59a18475c5b4)
Font 1: URCap structure.

### Toolbar Functionality

![Griper simulator](https://github.com/user-attachments/assets/26e849fd-0c54-451a-be58-cbdda7dcbf45)
Font 2: Griper simulator.

![Tweezer URCap](https://github.com/user-attachments/assets/fa073249-89ae-495a-8e63-dd73660fea99)
Font 3: Tweezer toolbar URCap.

Once you start the server, and open again the toolbar, it establishes a communication with socket in the port 12345 and the IP where the server is. 
![Opening the griper](https://github.com/user-attachments/assets/d258c7f8-10b7-4593-b627-c48a7e120c1f)
Font 4: Opening the griper.

![Closing the griper](https://github.com/user-attachments/assets/d4335343-0908-49db-ac43-658deaaeef86)
Font 5: Closing the griper.


