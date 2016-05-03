Application Details
=============

A test application for the Sky Technology Unattended Programming Test **Product Selection**

Prerequisites
-------------

* JDK1.8+
* Maven 3.3.9  (May work on earlier version but only tested on 3.3.9)
* Available Port 8080

Running
-------

* Set java classpath to use JDK1.8+
* Run the Maven command $mvn spring-boot:run

Testing
-------

Navigate to the http://localhost:8080 or select the [application link](http://localhost:8080)

The landing page provides four possible options to view products as a
customer from Liverpool, London or Leeds and finally as an invalid customer.

Selecting the _"View Products"_ option from the relevant panel will set a customerID
cookie prior to navigating to the products page.







