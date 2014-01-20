TTS
===

Task Tracker System - COMP 3350 Software Engineering 1

Re-uploaded onto GitHub.
Old version control system used was CVS.

Developed by:
Mathias Eurich
Duong Nguyen
Jaydee Sendin
Tony Young


To run the program:
----------------------------
-You may run these batch files using the command prompt (cmd)
-If the program has not been compiled yet then:
-In cmd, run Compile.bat
-Note: Compile.bat relies on if your computer has
Java installed as well as the Java function javac
-To reset the database to the original settings, run RestoreDB.bat using cmd located
in the database folder
-To save the current database to be the original settings, run BackupDB.bat using cmd
located in the database folder

When the program starts:
----------------------------
-A login screen will show up with a list of users already
populated in the drop down box
-Select any user and click the "Log In" button
-It does not matter what user you select, all the fields
displayed will be the same
-Alternatively, a new user can be created using the "Create User" button
-The user name can only be a max length of 32 characters consisting of alphanumeric
character and it cannot be empty

After logging in:
-This is the main window, in the white box, it will show the ALL tasks that are
currently in the system based on the database

Clicking "Create Task":
-After clicking this button, a different form will appear
showing all of the possible fields that the user can fill when
making a task
-Note that there are some restrictions on certain fields like
Time Estimate, Current Time Spent cannot be an alphabetical
value (yes, the program will complain if you try to do so)

-List of restrictions:
-Task Title: Cannot contain more than 64 characters, or contain 0 characters.
 All characters are legal
-Description and Comments: Cannot have more than 512 characters
-Time Estimate, Current Time Spent: Must be numeric

Clicking "Edit Task"
-After clicking this button, it will allow you to edit a selected
task by bringing up a different form
-This form will allow you to change the task title, assigned to user, status, priority,
due date, time estimate, current time spent, description and comments fields
-The created by, and created date fields CANNOT be changed

Clicking "Delete Task"
-After clicking this button, it will delete a selected task
-This task will not appear in the white box once deleted

Clicking "the column title name"
-In the white box where the tasks are displayed, if the name of the column is clicked
the tasks that are currently displayed will be sorted by the column that is selected
-Arrow up = ascending, arrow down = descending
-For numbers, arrow up means that the highest will be on the top of the view, and
the lowest will be on the bottom of the view and vice-versa for arrow down
-For dates, arrow up means that the chronologically highest date will be on the top
of the view, and the lowest will be on the bottom of the view, and vice-versa for
arrow down
-For text, arrow up means [numbers first/spaces to z ordering] (lexicographical) will
be on the top of the view, and the lowest will be on the bottom of the view and vice-versa
for arrow down

Clicking on "Apply Filter"
-This button will filter the current tasks that are displayed depending on the filter
criteria.
-The criteria that it is possible to filter by:
-Title: Given the text box, if the task title contains whatever is in the task box, it
will be displayed
-Assigned To: Will show the tasks showing only the tasks that of that assigned to
user
-Status: Will show the tasks showing only the tasks of the specified status
-Due between: Will show the tasks that have a due date between the ranges the
specified
-Priority: Will show the tasks that have a priority of the one specified by the filter
-The task filtering can be cumulative, meaning that a filter for a specific priority,
with a given status can be applied

Clicking on "Reset Filter"
-This button will reset any filters that have been applied
-The tasks displayed will be all the tasks that are currently in the database

Clicking "Sign Out"
-This will take you back to the login screen

Contents:
-----------------------------------
Scripts:
-Compile.bat
-Run.bat
-RunUnitTests.bat
-SetClasspath.bat

Database Scripts:
-RunHSQLDB.bat
-RestoreDB.bat
-BackupDB.bat
-OriginalSC.script
-OriginalSC.properties
-SC.script
-SC.properties

Folders:
-lib
-junit.jar
-hsqldb.jar
-swt.jar (for 64-bit Windows)
-swt-4.2.1-win32-win32-x86 (for 32-bit Windows)
-SWT-Readme (contains information about the SWT files)

-src (Contains all of the java source code files)

-tests (contains all the unit tests and within which package)
-business (tests for logic layer)
-TestAccessTasks.java
-TestAccessUsers.java
-TestBusiness.java
-TestCalculate.java
-objects (tests for domain specific objects)
-TestObjects.java
-TestPriorityCode.java
-TestStatusCode.java
-TestTask.java
-TestUser.java
-persistence (tests for database layer)
-TestDataAccessStub.java
-TestPersistence.java
-DataAccessStub.java
-AllTests.java

-tts (Contains all of the major source code files within which package)
-application (this is where the main java class is stored)
-Main.java
-Services.java (initializes the database)

-business (logic layer)
-AccessTasks.java
-AccessUsers.java
-Calculate.java

-objects (domain specific objects)
-Task.java
-User.java
-StatusCode.java (enum)
-PriorityCode.java (enum)

-persistence (database layer)
-DataAccess.java (interface for database)
-DataAccessObject.java (actual database)

-presentation (GUI layer)
-CreateTaskWindow.java
-CreateUserWindow.java
-StaticWindowMethods.java
-EditTaskWindow.java
-LogInWindow.java
-TaskMainViewWindow.java
-TaskWindow.java

-images
-image.txt
-task.png (picture used for the TTS)

-database
-database.txt (Information about the database .bat files)
-BackupDB.bat
-RestoreDB.bat
-RunHSQLDB.bat
-OriginalSC.script
-OriginalSC.properties
-SC.script
-SC.properties
-sqltool.rc

-bin
-this is where the .class files are stored