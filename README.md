# Distributed-Systems

The project was developed according to the course's (Distributed Systems - AUEB - 2023) guidelines . A completed Activity Tracker mobile app that allows a user to send his route in a GPX format and receive statistics for it. 

Firstly, the backend part of the application was developed. Through 4 different java files, the master-worker scheme was established and the mock application was ready to receive a file, read it and direct parts of it to different workers. The communication between the master node and the worker nodes happens via network. This allows the worker nodes to be different entities-computers, thus rendering the project scalable. 
Afterwards, the frontend was created with the use of Android Studio. A simple android application was set up along with a layout that allowed the user of the mobile application to upload his route and have his route's statistics shown to him, such as distance walked, elevation gained and time passed. The combination of these two, make up a working smartphone app.



