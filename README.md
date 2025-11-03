# Contacts API

## :tea: Introduction

Contacts-API is a REST API for managing contact lists as an alternative to the Google Contacts application.
It does not include all the features of Google Contacts, but we will add more features in the future.

The next image is a representation with UML of the general context of the application. It's based upon the [C4Model](https://c4model.com/) diagrams.

![Context diagram about application](docs/diagrams/images/Contacts_API___Context_Diagram_C4Model-Context_Diagram_MS_Contacts.svg)

![Container diagram about application](docs/diagrams/images/Contacts_API___Container_Diagram_C4Model-Container_diagram_for_Contact_Management_System.svg)

:book: View project docs. [Here](https://github.com/untalsanders/contacts-api/wiki).

## :rocket: How to start

   1. Install Java 17, go link for install [SDKMAN](https://sdkman.io/install)

      ```shell
      sdk install java x.y.z-vendor
      ```

   2. Set it as your default JVM

      ```shell
      export JAVA_HOME='$HOME/.sdkman/candidates/java/current'
      ```

   3. Clone this repository:

      ```shell
      git clone https://github.com/untalsanders/contacts-api.git
      ```

   4. Run application

      ```shell
      # Dev Environment
      ./gradlew bootRun --args='--spring.profiles.active=dev --server.port=8080'
      ```
      ```shell
      # Prod Environment
      ./gradlew bootRun --args='--spring.profiles.active=prod --server.port=8080'
      ```

      To `production` you must provide as following variables environment, e.g.

      - DB_HOST=127.0.0.1
      - DB_PORT=5432
      - DB_USER=postgres
      - DB_PASS=12345
      - DB_NAME=my_db

      **Example:**

      ```shell
      # Prod Environment
      DB_HOST=localhost DB_PORT=5432 DB_USER=postgres DB_PASS=12345 DB_NAME=contacts_api ./gradlew bootRun --args='--spring.profiles.active=prod --server.port=8080'
      ```

## :whale: Build and Run with Docker

   1. **Build Image**

      ```shell
      # Build image
      docker build -t sandersgutierrez/contacts-api:latest .
      ```

   2. **Run a container**

      ```shell
      # Create and run a container
      docker run -p 8080:8080 --name contacts-api \
          --env PORT=8080 \
          --env DB_HOST=localhost \
          --env DB_PORT=5432 \
          --env DB_NAME=contacts_api \
          --env DB_USER=postgres \
          --env DB_PASS=12345 \
          sandersgutierrez/contacts-api:latest
      ```

## :wolf: Author

- [Sanders Gutiérrez](https://untalsanders.com) - System Engineer (Java Developer)

## :unlock: License

This project is open-sourced software licensed under the [AGPL-3.0 licence](LICENSE).
