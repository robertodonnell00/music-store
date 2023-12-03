# music-store

# Overview

The Music Store App is a web application designed to manage customer information and instrument purchases for a music store. This app allows store administrators to keep track of customers and the instruments they've bought.

# Features

- **Customer Management:** Store and manage customer information, including name, contact details, and purchase history.

- **Instrument Tracking:** Record and view the instruments purchased by each customer, including details such as instrument type, brand, and purchase date. Can view instruments independently or as attached to a Customer. Easy option to select instrument type/menu by generating pre-made responses when creating/updating instrument type or customer's preferred type of instrument.

- **CRUD:** Can create, read, update and delete customers and instruments from the menu.

- **User-Friendly Interface:** Intuitive and easy-to-use interface for both Instruments and customers.

- **Search and Filter:** Efficiently search and filter customer and instrument data based on various criteria.

- **Generate a reciept:** Generate receipt for Customers based on Instruments they have purchased. If they received the instrument but have not yet paid, the amount they owe will also be tracked. Also, if the user has spent €500 or more, they are eligible for VIP status which can be updated after generating receipt.

- **Persistence:** System uses 3 different persistences, Yaml, JSON, and XML. User can switch between each persistence type from the menu.

- **JUnit Testing:** JUnit testing was used throughout the system to ensure code runs as intended.

- **Security:** Light security was introduced as a safety measure for the store.

- **Validation:** Some validation was introduced to minimise errors in data, such as: Can't have a number in customer name, customer name can't be longer than 15 characters, review scores can't be less than 0 or more than 100, etc.

- **Customer Satisfaction:** Store's can now get the average review from all instruments sold. This will help the store to gather feedback.

# Help Received

Had trouble managing the Mutable Set of Instruments, received direction from my friend Padraig Crotty who is a Java Developer. He showed me how to cast into a Mutable set, eg:
 (itemsBought.filter { instrument -> instrument.instrumentID == searchInt }).toMutableSet()
ChatGPT was used to generate the KDoc.
The rest of the project was all my own work, after having learned Kotlin programming during Software Systems Development module by lecturer Siobhan Drohan.

