# ProjectManagerApp

Ứng dụng desktop quản lý dự án (Bug/Feature) viết bằng **Java 21 + JavaFX**, kết nối **SQL Server** qua JDBC. Có phân quyền User/Admin, đăng nhập/đăng ký, thêm/sửa/xóa task, thống kê theo priority/status.

> Dự án học tập — áp dụng OOP, Design Patterns (Singleton, Factory), Generics, Exception Handling, Multithreading, JDBC.

## Công nghệ sử dụng

| Thành phần | Phiên bản |
|---|---|
| Java (JDK) | 21 |
| JavaFX | 21.0.10 |
| Maven | javafx-maven-plugin |
| Database | SQL Server (JDBC driver `mssql-jdbc` 13.4.0.jre11) |

## Cấu trúc thư mục

```
src/main/java/com/projectmanager/
├── MainApp.java                # Entry point JavaFX
├── config/DatabaseConfig.java  # Kết nối SQL Server
├── exceptions/AppException.java
├── factory/TaskFactory.java    # Factory Pattern: tạo Bug/Feature
├── models/                     # Task, Bug, Feature + interfaces (ITask, IAssignable, ISeverityRatable, IPersistable, IProjectAnalytics)
│   ├── dto/LoginRequest.java
│   └── entity/User.java
├── repository/                 # TaskRepository, UserRepository — thao tác DB
├── service/                    # AuthService (login/register), ProjectService (Singleton + Generic)
├── session/UserSession.java    # Giữ trạng thái đăng nhập (Singleton)
├── ui/
│   ├── SceneSwitcher.java      # Chuyển màn hình FXML
│   └── controllers/            # Controller cho từng màn hình FXML
└── utils/                      # PasswordHasher (SHA-256), Validator
```

## Yêu cầu môi trường

- JDK 21 (khuyến nghị Homebrew: `brew install openjdk@21`)
- Maven 3.9+
- SQL Server đang chạy tại `localhost:1433`, database tên `ProjectManagerDB`

Set `JAVA_HOME` trỏ đúng JDK 21 trước khi build (project pin `maven.compiler.release=21`):

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
```

## Cài đặt & chạy

```bash
mvn compile          # tải dependency + build
mvn javafx:run        # chạy ứng dụng
```

Thông tin kết nối DB khai báo tại [`DatabaseConfig.java`](src/main/java/com/projectmanager/config/DatabaseConfig.java) — cập nhật `URL`/`USER`/`PASSWORD` theo môi trường của bạn trước khi chạy.

## Tiến độ

| Bước | Nội dung | Trạng thái |
|---|---|---|
| 00 | Setup project (Maven + JavaFX) | ✅ |
| 01 | Core Models (Task, Bug, Feature, interfaces) | ✅ |
| 02 | Data Layer (Repository, DatabaseConfig) | ✅ |
| 03 | Service (TaskFactory, AuthService, ProjectService) | ✅ |
| 04 | Utils / Session / Exception | ✅ |
| 05 | Controllers | 🚧 Đang làm — SceneSwitcher, LoginController xong; còn Register/Dashboard/AddTask/TaskList/UserList |
| 06 | FXML + CSS (giao diện) | ⏳ Chưa bắt đầu |

## Tính năng dự kiến

- Đăng nhập / Đăng ký tài khoản, mật khẩu hash SHA-256
- Phân quyền User / Admin (Admin quản lý user, xóa task)
- Thêm Bug / Feature qua Factory Pattern
- Danh sách task: lọc theo status/type, cập nhật status, xóa (admin)
- Dashboard thống kê: tổng effort, số lượng theo priority/status
- Load dữ liệu bất đồng bộ (background thread) để không đứng UI
