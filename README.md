### **Product/Inventory management RESTful API to demonstrate Create, Read, Update and Delete operations using Spring Boot.**  

  
## 🔥 **Features**  
1️⃣ Uses in-memory storage using ConcurrentHashMap to ensure thread-dafety without overhead locking (Upgradeable to external databases in future)  
2️⃣ Uses MapStruct for conversion between Product model and Product DTO (Data Trasnfer Object). Easier transfer between Controller <-> DTO <-> Repository  
3️⃣ Uses Lombok for simpler annotation  
4️⃣ Unit test with JUnit5 for testing without needing to start the server and Integration test to mock HTTP requests against controller and test the full CRUD flow using MockMVC and then create a JaCoCo report.

5️⃣ Application containerized in 2 stage (Build & Run stages) Docker file for easy deployment and smaller image size  
6️⃣ Automated build and test using GitHub Actions to ensure code quality before merging into Main branch  
**To-Do** : Integrate Redis for caching , Add database for data persistence , Expand the Error Handling class for more detailed error communication


  
## 🚀 **Running the Application Locally**  
### Prerequisites:  
- **Java 17** (OpenJDK or Oracle JDK)  
- **Maven 3.8+**  
### Steps:  
1️⃣ **Clone the Repository:**  
git clone https://github.com/krazyfingerz/CRUD-backend-Java-SpringBoot  
cd productapi  
2️⃣ **Build locally:**  
./mvnw clean install  
3️⃣ **Run Application**  
./mvnw spring-boot:run  
4️⃣ **Tests**
./mvnw test

**Build Container** : docker build -t product-api .  
**Run Container** : docker run -d -p 8080:8080 product-api


