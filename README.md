## GIT FOR HUMANS

Git for Humans is a prototype for a Git-based cloud storage system (AKA a glorified remote repository with responsive design so that accountants can actually use it). The core GOAL is to merge the best parts of two different web applications: a cloud storage service and a remote repository.

~This project is being developed using an AGILE methodology consisting of exactly Two Sprints. These sprints are systematically divided by the precise moment I decide to sleep and how much time I have left before the deadline.~

#### The project includes the following functionalities:
* **Simplified Git Usage:** ADD, COMMIT, CLONE, and PUSH.
* **Smart Large File Tracking:** A custom, lightweight pointer approach (no Git LFS needed).
* **User Sessions & Core CRUD:** Fully integrated PostgreSQL database.
* **User Permissions:** Proper repository collaborator management.

###  MILESTONE ROADMAP (AKA TODO LIST)

####  DATABASE
* [X] Create PostgreSQL Schema
* [X] Implement the 5 Core Tables (`usuarios`, `repositorios`, `colaboradores`, `archivos_metadata`, `human_diffs`)

####  WEB-APP ARCHITECTURE (MVC)
*> Both POJOs and JSPs are included here, but the specific requirements of "View and User Experience" and "Git Logic" must be fulfilled to complete this section.*
* [ ] POJOs (Java Beans, Auxiliars and general Logic)
* [X] DAOs (Data Access Objects for PostgreSQL)
* [X] Servlets (Controllers)
* [X] JSPs (Views)

####  VIEW AND USER EXPERIENCE
* [X] LOGIN PAGE
* [X] REGISTER PAGE
* [X] LANDING PAGE
* [X] DASHBOARD
* [X] "REPOSITORY" PAGE (A clean UI hybrid between a code repo and a cloud folder)
* [X] COLLABORATOR PAGE
* [X] Standard HTML5 File Upload Integration

####  GIT LOGIC
* [ ] CHANGES CAN BE SENT TO REMOTE REPOSITORY
* [ ] CHANGES CAN BE MADE IN REMOTE REPOSITORY

####  APP LOGIC
* [ ] LARGE FILE MANAGEMENT THROUGH "HUMAN DIFF" (Metadata-based versioning)
* [ ] STRICT PERMISSION MANAGEMENT (Read/Write access for collaborators)

So things have changed since the deadline have changed, The current organization in SPRINTS for this project is the following

## DEVELOPMENT ROADMAP (AKA SPRINTS)

The project follows a structured, milestone-driven development cycle optimized for an independent development workflow (AKA Hydrogen bomb vs Coughing baby and i'm the coughing baby). The roadmap is divided into 5 tactical sprints focusing on progressive layer architecture, from data persistence to cloud deployment.

### SPRINT 1 — Data Infrastructure & Core Architecture
**Timeline:** 06/06/26
* **Database Design:** Creation and execution of the relational schema in PostgreSQL (Implementation of the 5 core entities: Users, Repositories, Collaborators, Metadata, and Human Diffs).
* **MVC Base Setup:** Initialization of the web application framework using Maven, configuring the embedded Tomcat container, dependencies, and project folder hierarchy.
* **Data Persistence Layer:** Coding the JavaBeans (Models) and Data Access Objects (DAOs) to lay down the bare-bones database communication.
* **Real End Time:** 07/06/26 18:10

### SPRINT 2 — View Mapping & Fully Functional CRUD
**Timeline:** 08/06/26
* **UI/UX Page Mapping:** Full interface routing using JavaServer Pages (JSP) and standard Controller Servlets.
* **CRUD Integration:** Connecting the front-end views (JSP) and Controllers (Servlets) directly to the DAOs. Getting data to actually render on screen and seamlessly process user inputs.
* **Access Control:** Development of basic session handling and repository-level permission rules (Owner vs. Collaborator logic).
* **Real End Time:** 08/07/26 16:18

### SPRINT 3 — Version Control Engine & Custom LFS
**Timeline:** 08/06/26
* **Core Git Logic:** Embedding the JGit library to handle native local repository operations (`Init`, `Add`, `Commit`, `Clone`, `Push`) seamlessly from Java code.
* **Smart Large File Tracking:** Implementation of the lightweight metadata pointer system (`.pointer` JSON architecture) to bypass heavy binary processing on the local file tree.
* **Human Diff Validation:** Programming the conditional execution logic to distinguish between text rewordings and full binary updates based on user input.

### SPRINT 4 — Cloud Infrastructure Integration
**Timeline:** 09/06/26
* **Azure Environment Provisioning:** Setup and configuration of the cloud runtime inside Azure for Students (Azure App Service and environment variables).
* **Cloud Storage Bridge:** Integration of the Azure Blob Storage Java SDK to manage real-time file streams, direct binary uploads, and secure URL generation.

### SPRINT 5 — Production Deployment & Validation
**Timeline:** 09/06/26
* **Live App Deployment:** Final build packaging and deployment of the application container to Azure App Service.
* **End-to-End Testing:** Rigorous exception handling validation, connection pool debugging, and live feature verification to guarantee the app doesn't crash during the presentation.
