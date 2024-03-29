![coleguita_recortado](https://user-images.githubusercontent.com/104777178/205708178-5c402931-34a3-4a97-b327-707c1ae15942.png)
### A gardening tool for house plants dummies.

This project was developed as a final project of my Higher Technical Certificate in development of multi-platform applications. Aimed to be a user-friendly gadget
to help non-experts in plant care. The objective of the project was to put my learning on android and REST architecture into practice. 
Basically the interesting thing of the whole project is the idea of keeping the three main components (the board with the sensors, the android app, and the database)
independent of each other.


![ESQUEMA REST(recortado)](https://user-images.githubusercontent.com/104777178/207394025-697be728-7096-4b8c-ac35-af793dfc306b.png)

# Hardware

The main component of the device is the nodeMCU v3 with a ESP8266 microchip for the WiFi connection.

  <img src= "https://user-images.githubusercontent.com/104777178/207998554-18305bbb-8835-46b4-b9ea-303be09b1ffb.JPG" width="400" height="266"/>


For measuring ambient temperature and humidity the main board uses the DHT22.

  <img src= "https://user-images.githubusercontent.com/104777178/207998750-85e7f0fb-6437-4b71-bdee-c3f81b015b29.jpg" width="400" height="266"/>
  
Next we have the capacitive soil moisture sensor v2.0.

  <img src= "https://user-images.githubusercontent.com/104777178/208001027-72459345-d642-427f-8ac9-5c4ef9058ec6.jpg" width="400" height="266"/>
  
The last two components are the power supply MB102 and a 9V battery.
<p float="left">
  <img src= "https://user-images.githubusercontent.com/104777178/208714764-59333f8c-eef6-4fd3-aeb2-b2c1a38f31ac.jpg" width="266" height="266"/>

  <img src= "https://user-images.githubusercontent.com/104777178/208714776-e844bb18-03d6-424c-ae03-2a6dcc2ca395.jpg" width="266" height="266"/>
</p>

### Assembly

<p float="left">
  <img src= "https://user-images.githubusercontent.com/104777178/208717640-a30d68a9-dcab-4151-80fc-2774b6e6dcb6.jpg" width="350" height="266"/>
  <img src= "https://user-images.githubusercontent.com/104777178/208718389-c43a5b04-babb-46b4-9d91-9393185f5c24.jpg" width="200" height="266"/>
  <img src= "https://user-images.githubusercontent.com/104777178/222774524-22aedb1e-9b87-4ee5-83d5-d2148df9f40b.PNG" width="350" height="266"/>
</p>


### Designing the 3D printed case
<p float="left">
  <img src= "https://user-images.githubusercontent.com/104777178/208721249-14a988fd-644b-4418-8a61-5a4c0e813bc2.png" width="400" height="266"/>
  <img src= "https://user-images.githubusercontent.com/104777178/208721400-6e50a7ea-4bfa-4be2-8823-0dc2e1821c02.jpg" width="400" height="266"/>
</p>

# Software

The Android app is the main interface for the user to interact with the product. In this app the user can create an account linked to the dispositive ID, register an unlimited number of dispositives and pots and check the readings of the last day/month/year.
<p align="left">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/226712724-d2c94e77-f9e8-4ed0-abb8-1fab45351547.jpg">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/226717566-64ba6986-4d5f-4ef3-8ad4-84aa86fe930e.jpg">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/227992678-4ac01aad-10e0-4b4e-b18b-3ec4337bcce5.jpg">
</p>
Simple login screen. For trial purposes you can use prueba as user and password. The creation of a new plant is also very straightforward, just name it, enter your device code, and select a picture of your plant.
<br />
<br />
<br />
<p align="left">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/227992676-52e5af42-a8d4-49f0-91a5-06a4576d9804.jpg">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/227993945-ca99c0c0-5a57-4752-b354-9e903f6ee0d9.jpg">
  <img width="25%" height="17%" src="https://user-images.githubusercontent.com/104777178/227992667-e618d760-b4f6-4c50-8f5f-76be2f7df9f8.jpg">
</p>

Once you have enough data from your device, you can check all of the measurings in this screen. For now these measures are soil moisture, temperature and humidity. 
