# 🧪 OrangeHRM Automation Testing Demo Project
![Java](https://img.shields.io/badge/Language-Java-blue)
![Allure](https://img.shields.io/badge/Reporting-Allure-purple)

This is a **demo automation testing project** for the [OrangeHRM](https://www.orangehrm.com/) web application. It focuses on automating critical end-to-end user scenarios using the **TestNG framework**, **Page Object Model (POM)**, and **Fluent Interface Pattern**.

---

## 🔧 Tech Stack

- **Language**: Java  
- **Testing Framework**: TestNG  
- **Design Pattern**: Page Object Model (POM)  
- **Coding Style**: Fluent Interface Pattern  
- **Test Listener**: Custom TestNG Listeners  
- **Reporting**: Allure TestNG annotations  
- **Test Data Management**: JSON  
- **Browser Control**: DriverManager utility  
- **Property Management**: Config file via `PropertiesUtils`

---

## ✅ Test Scenarios Covered

### 🔐 Login Module
- ✅ Successful login with valid credentials  
- ❌ Login attempt with invalid credentials  
- ❌ Login with empty fields

### 👨‍💼 PIM Module – Employee Management
- ✅ Add new employee  
- ✅ Add employee and create user  
- ❌ Add employee with:
  - Existing username  
  - Existing employee ID  
  - Invalid/empty fields  

### 👤 Admin Module – User Management
- ✅ Add new system user  
- ❌ Add user with:
  - Existing username  
  - Invalid employee name (auto-complete fails)  
  - Missing required fields

---

## 📁 Project Structure
  com.oragneHRM/
  ├── drivers/ → Browser Driver Manager
  ├── listeners/ → TestNG Listeners
  ├── pages/ → POM Classes (LoginPage, PIMPage, etc.)
  ├── tests/ → TestNG Test Classes (E2ETest.java)
  ├── utils/ → Utility Classes (JSON, Properties, Actions)
  └── resources/
  └── test-data.json → Test Data

---

## 🔄 Design Patterns & Architecture

- **Page Object Model (POM)**: Each web page is modeled as a Java class.
- **Fluent Interface Pattern**: Improves readability via method chaining.
- **Data-Driven Tests**: External JSON-based test data.
- **Modular Test Structure**: Reusable utilities, listeners, and dynamic browser support.

---

## 📌 Test Grouping & Tagging

Tests are grouped and tagged using TestNG:
- `@Test(groups = {"smoke", "regression", "login"})`
- `@Tag("regression")`, `@Tag("addEmployee")`, etc.

This allows selective test execution (e.g., smoke only or regression suite).

---

## 🚀 How to Run the Tests

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/orangehrm-automation.git
   cd orangehrm-automation
2. Install dependencies via Maven or your IDE

3. Update Configuration
    browserType=chrome

4. Run Tests
    From TestNG XML suite
    Or directly via IDE
5.Generate Allure Report (if integrated)

📈 Future Enhancements
  Integrate CI/CD with GitHub Actions or Jenkins
  1. Add data-driven testing with TestNG @DataProvider
  2. Parallel execution support
  3. Extended cross-browser testing
  4. Docker + Selenium Grid integration


