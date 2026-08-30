# Quiz Web

### This application will be yet another release of the same idea. Here are some key features:

- ##### Each user will be able to sign up to the quiz and then login to retrieve the data
- ##### Point of logging in is to assign every question its own 'set'
- ##### Each user has an unlimited number of sets
- ##### There are 2 types of users: ``User``, ``Admin``
- ##### ``User`` can:
     - Create a set
     - Modify a set
     - Delete a set
     - Start a quiz on selected set
- #### ``Admin`` can:
     - Delete any set
     - Delete any user
     - Change username and role of any user
### Questions will be in 5 types:

- ##### ``A/B/C/D`` (closed)
- ##### ``TRUE/FALSE`` (closed)
- ##### ``YES/NO`` (closed)
- ##### ``DD/MM/YYYY`` (data)
- ##### ``Insert answer here`` (open)

### At the end of each quiz the user will see how many question they got
``RIGHT/WRONG``, Quiz also shows the user which questions they got wrong, what was the correct answer and how many points said question was worth

### This app follows REST API architecture (frontend generates HTML) in the first release and MVC architecture (backend generates HTML) in the following releases

## Current version is set up to run in docker, if you wish to run it locally change uri in ``aplication.properties`` to:

## ``mongodb://root:secret@localhost:27017/quizdatabase?authSource=admin``

### Technologies I am using:
- ##### ``Spring WebMvc`` 
- ##### ``Spring Data MongoDB``
- ##### ``Spring Security``
- ##### ``Spring Validation``
- ##### ``Thymeleaf``
- ##### ``Lombok`` 
- ##### ``Docker`` 
- ##### ``Postman``
- ##### ``MongoDB`` 
- ##### ``Java 26``
- ##### ``HTML 5`` 
- ##### ``CSS 3``
- ##### ``JavaScript`` 

### Setup:

* Firmware version: Linux Mint 22.3 - Cinnamon 64-bit
* Hardware: AMD Ryzen 5 AI 340, 16GB RAM
* Toolchain: IntelliJ, MongoDB, Docker
* SDK: Java 26

# !Project finished!

#### Started on ``21th of July 2026`` finished on ``30th of August 2026``

#### Documentation can be found here: ``https://github.com/Uboatwaffe/JavaDoc.git``