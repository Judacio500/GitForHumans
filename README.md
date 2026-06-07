## GIT FOR HUMANS

Git for Humans is a prototype for a Git-based cloud storage system (AKA a glorified remote repository with responsive design so that accountants can actually use it). The core GOAL is to merge the best parts of two different web applications: a cloud storage service and a remote repository.

This project is being developed using an AGILE methodology consisting of exactly Two Sprints. These sprints are systematically divided by the precise moment I decide to sleep and how much time I have left before the deadline.

#### The project includes the following functionalities:
* **Simplified Git Usage:** ADD, COMMIT, CLONE, and PUSH.
* **Smart Large File Tracking:** A custom, lightweight pointer approach (no Git LFS needed).
* **User Sessions & Core CRUD:** Fully integrated PostgreSQL database.
* **User Permissions:** Proper repository collaborator management.

###  TODO LIST

####  DATABASE
* [ ] Create PostgreSQL Schema
* [ ] Implement the 5 Core Tables (`usuarios`, `repositorios`, `colaboradores`, `archivos_metadata`, `human_diffs`)

####  WEB-APP ARCHITECTURE (MVC)
*> Both JavaBeans and JSPs are included here, but the specific requirements of "View and User Experience" and "Git Logic" must be fulfilled to complete this section.*
* [ ] JavaBeans (Models)
* [ ] DAOs (Data Access Objects for PostgreSQL)
* [ ] Servlets (Controllers)
* [ ] JSPs (Views)

####  VIEW AND USER EXPERIENCE
* [ ] LOGIN PAGE
* [ ] REGISTER PAGE
* [ ] LANDING PAGE
* [ ] DASHBOARD
* [ ] "REPOSITORY" PAGE (A clean UI hybrid between a code repo and a cloud folder)
* [ ] COLLABORATOR PAGE
* [ ] Standard HTML5 File Upload Integration

####  GIT LOGIC
* [ ] CHANGES CAN BE SENT TO REMOTE REPOSITORY
* [ ] CHANGES CAN BE MADE IN REMOTE REPOSITORY

####  APP LOGIC
* [ ] LARGE FILE MANAGEMENT THROUGH "HUMAN DIFF" (Metadata-based versioning)
* [ ] STRICT PERMISSION MANAGEMENT (Read/Write access for collaborators)
