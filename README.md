# Hệ Thống Quản Lý Vé Tàu (Train Ticket Management System)

## 📋 Giới thiệu

Hệ thống quản lý vé tàu là một ứng dụng web backend được phát triển bằng Spring Boot, cung cấp các chức năng quản lý và đặt vé tàu trực tuyến. Hệ thống hỗ trợ quản lý toàn diện từ thông tin tàu, lộ trình, lịch trình cho đến việc đặt vé và quản lý hành khách.

## 🚀 Công nghệ sử dụng

### Backend Framework
- **Spring Boot 3.4.3** - Framework chính
- **Java 17** - Ngôn ngữ lập trình
- **Maven** - Quản lý dependencies và build tool

### Database & ORM
- **MySQL** - Cơ sở dữ liệu quan hệ
- **Spring Data JPA** - ORM framework
- **Hibernate 6.2.0** - JPA implementation

### Security
- **Spring Security** - Xác thực và phân quyền
- **JWT (JSON Web Token)** - Token-based authentication
- **BCrypt** - Mã hóa mật khẩu

### Caching & Session
- **Redis** - Cache và session management

### Additional Libraries
- **Lombok** - Giảm boilerplate code
- **Bean Validation** - Validation dữ liệu
- **Thymeleaf** - Template engine
- **Spring Mail** - Gửi email
- **ZXing** - Tạo QR code cho vé

### Testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **Selenium WebDriver** - Automated testing
- **Cucumber** - BDD testing

## 📦 Cấu trúc dự án

```
Train_ticket_management/
├── src/
│   ├── main/
│   │   ├── java/com/tauhoa/train/
│   │   │   ├── TrainApplication.java          # Main application
│   │   │   ├── common/                         # Common utilities
│   │   │   ├── configurations/                 # Security, CORS configs
│   │   │   ├── controllers/                    # REST Controllers
│   │   │   ├── dtos/                          # Data Transfer Objects
│   │   │   │   ├── request/                   # Request DTOs
│   │   │   │   └── response/                  # Response DTOs
│   │   │   ├── exceptions/                     # Custom exceptions
│   │   │   ├── models/                         # JPA Entities
│   │   │   ├── repositories/                   # Data repositories
│   │   │   ├── securities/                     # Security components
│   │   │   ├── services/                       # Business logic
│   │   │   ├── utils/                          # Utility classes
│   │   │   └── validators/                     # Custom validators
│   │   └── resources/
│   │       └── application.yml                 # Application configuration
│   └── test/
│       ├── java/                               # Test classes
│       └── features/                           # Cucumber feature files
├── db_train_ticket_system.sql                  # Database schema
├── Dockerfile                                   # Docker configuration
├── pom.xml                                      # Maven configuration
└── README.md                                    # Project documentation
```

## 🗄️ Mô hình cơ sở dữ liệu

Hệ thống bao gồm các bảng chính sau:

### Core Entities
- **Train** (Tàu) - Thông tin về các đoàn tàu
- **Station** (Ga) - Thông tin các ga tàu
- **Route** (Tuyến đường) - Các tuyến đường tàu
- **RouteDetail** - Chi tiết các ga trong tuyến
- **Trip** (Chuyến đi) - Các chuyến đi cụ thể
- **TrainSchedule** (Lịch trình) - Lịch trình tàu

### Carriage & Seat Management
- **Compartment** (Toa tàu) - Các loại toa tàu:
  - Ngồi mềm điều hòa (1.0x)
  - Giường nằm khoang 6 điều hòa (1.2x)
  - Giường nằm khoang 4 điều hòa (1.4x)
- **CarriageList** - Danh sách toa của từng chuyến
- **Seat** (Ghế ngồi) - Thông tin ghế/giường

### Booking & Customer
- **Customer** (Khách hàng) - Thông tin khách hàng
- **Passenger** (Hành khách) - Thông tin hành khách
- **Ticket** (Vé) - Thông tin vé đã đặt
- **TicketType** (Loại vé) - Các loại vé (người lớn, trẻ em, sinh viên...)
- **ReservationCode** (Mã đặt chỗ) - Mã đặt vé

### User Management
- **User** - Tài khoản người dùng hệ thống

## 🔌 API Endpoints

Hệ thống cung cấp các RESTful API sau:

### Authentication & User
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký
- `POST /api/users/change-password` - Đổi mật khẩu

### Train Management
- `GET /api/trains` - Lấy danh sách tàu
- `POST /api/trains` - Thêm tàu mới
- `PUT /api/trains/{id}` - Cập nhật thông tin tàu
- `DELETE /api/trains/{id}` - Xóa tàu

### Station Management
- `GET /api/stations` - Lấy danh sách ga
- `POST /api/stations` - Thêm ga mới
- `PUT /api/stations/{id}` - Cập nhật ga
- `DELETE /api/stations/{id}` - Xóa ga

### Route Management
- `GET /api/routes` - Lấy danh sách tuyến
- `POST /api/routes` - Thêm tuyến mới
- `GET /api/routes/{id}` - Chi tiết tuyến

### Trip Management
- `GET /api/trips` - Lấy danh sách chuyến đi
- `POST /api/trips` - Thêm chuyến đi mới
- `GET /api/trips/search` - Tìm kiếm chuyến đi
- `GET /api/trips/{id}/seats` - Xem ghế trống

### Ticket Booking
- `POST /api/tickets/book` - Đặt vé
- `GET /api/tickets/{id}` - Chi tiết vé
- `GET /api/tickets/search` - Tìm kiếm vé
- `DELETE /api/tickets/{id}` - Hủy vé

### Customer & Passenger
- `GET /api/customers` - Danh sách khách hàng
- `POST /api/customers` - Thêm khách hàng
- `GET /api/passengers` - Danh sách hành khách

### Seat Management
- `GET /api/seats/availability` - Kiểm tra ghế trống
- `GET /api/carriages` - Danh sách toa tàu

### Train Schedule
- `GET /api/train-schedules` - Lịch trình tàu
- `POST /api/train-schedules` - Thêm lịch trình

## ⚙️ Cấu hình

### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/train_ticket_system
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Redis Configuration
```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

### JWT Configuration
```yaml
jwt:
  expiration: 10000              # 10 seconds (access token)
  refresh-expiration: 86400      # 1 day (refresh token)
  secretKey: [your-secret-key]
```

### Email Configuration
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: [your-email]
    password: [app-password]
```

## 🚢 Cài đặt và Chạy ứng dụng

### Yêu cầu hệ thống
- Java 17 trở lên
- Maven 3.6+
- MySQL 8.0+
- Redis Server
- Docker (optional)

### Cài đặt thủ công

1. **Clone repository**
```bash
git clone [repository-url]
cd Train_ticket_management
```

2. **Tạo database**
```bash
mysql -u root -p < db_train_ticket_system.sql
```

3. **Cấu hình application.yml**
- Cập nhật thông tin database
- Cấu hình email (nếu cần)
- Cấu hình Redis connection

4. **Build project**
```bash
./mvnw clean install
# Hoặc trên Windows
mvnw.cmd clean install
```

5. **Chạy ứng dụng**
```bash
./mvnw spring-boot:run
# Hoặc
java -jar target/train-0.0.1-SNAPSHOT.jar
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

### Chạy với Docker

1. **Build Docker image**
```bash
./mvnw clean package
docker build -t train-ticket-system .
```

2. **Chạy container**
```bash
docker run -p 8080:8080 train-ticket-system
```

## 🧪 Testing

### Chạy Unit Tests
```bash
./mvnw test
```

### Chạy Integration Tests
```bash
./mvnw verify
```

### Chạy Cucumber Tests
```bash
./mvnw test -Dtest=TrainSearchTest
```

### Test Files
- `TrainSearchTest.feature` - BDD test scenarios
- `TrainSearchTest.java` - Selenium-based tests
- Controller tests trong `src/test/java/controllers/`
- Service tests trong `src/test/java/services/`

## 🔐 Security

Hệ thống sử dụng JWT để xác thực:

1. **Login**: POST `/api/auth/login` với username và password
2. **Response**: Nhận access token và refresh token
3. **Authorization**: Thêm header `Authorization: Bearer [token]`
4. **Token Refresh**: Sử dụng refresh token để lấy access token mới

### Password Encoding
- Sử dụng BCrypt để hash password
- Độ mạnh: 10 rounds

### CORS Configuration
- Được cấu hình trong `CorsConfig.java`
- Cho phép cross-origin requests từ frontend

## 📧 Email Notifications

Hệ thống gửi email cho các sự kiện:
- Xác nhận đặt vé
- Thông báo hủy vé
- Thông tin vé kèm QR code

## 🎯 Tính năng chính

### 1. Quản lý Tàu và Lịch trình
- Thêm, sửa, xóa thông tin tàu
- Quản lý lịch trình tàu
- Quản lý tuyến đường và các ga

### 2. Đặt vé
- Tìm kiếm chuyến tàu theo ngày, ga đi, ga đến
- Chọn loại toa (ngồi mềm, giường nằm khoang 6, khoang 4)
- Chọn ghế/giường cụ thể
- Áp dụng giảm giá theo loại vé

### 3. Quản lý Khách hàng
- Đăng ký tài khoản
- Quản lý thông tin cá nhân
- Xem lịch sử đặt vé

### 4. Quản lý Hành khách
- Thêm nhiều hành khách trong một đơn đặt
- Lưu thông tin hành khách thường xuyên

### 5. Hệ thống Tính giá
- Giá cơ bản theo cự ly
- Hệ số giá theo loại toa
- Giảm giá theo loại vé (trẻ em, sinh viên, người cao tuổi)

### 6. QR Code
- Tự động tạo QR code cho mỗi vé
- Scan QR code để kiểm tra vé

### 7. Báo cáo và Thống kê
- Thống kê doanh thu
- Thống kê số lượng vé bán
- Tỷ lệ lấp đầy chỗ

## 🏗️ Design Patterns

Dự án áp dụng các design patterns:
- **MVC Pattern** - Model-View-Controller
- **Repository Pattern** - Data access abstraction
- **Service Layer Pattern** - Business logic separation
- **DTO Pattern** - Data transfer objects
- **Dependency Injection** - Spring IoC container
- **Builder Pattern** - Lombok @Builder
- **Singleton Pattern** - Spring beans

## 📝 Logging

- Sử dụng SLF4J với Logback
- Log level: WARN (có thể cấu hình trong application.yml)
- Log format: timestamp, level, thread, logger, message

## 🔄 Data Loading

`InitialDataLoader.java` - Component để load dữ liệu mẫu khi khởi động ứng dụng (nếu cần)

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.

## 👥 Team

Dự án được phát triển bởi nhóm Tauhoa Team.

---

**Note**: Đây là phiên bản backend API. Frontend application cần được phát triển riêng để tương tác với các API endpoints này.
