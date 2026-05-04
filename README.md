# 🤖 AI-Driven Test Automation Framework
### Selenium WebDriver + TestNG | Java | Claude AI + GitHub Copilot

![Tests](https://img.shields.io/badge/Tests-24%2F24%20Passing-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-43B02A)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red)
![AI](https://img.shields.io/badge/AI-Claude%20%2B%20Copilot-blueviolet)
![Pattern](https://img.shields.io/badge/Pattern-Page%20Object%20Model-blue)

---

## 📌 Project Overview

This project demonstrates a **complete end-to-end AI-assisted software testing workflow** applied to the e-commerce web application [automationexercise.com](https://automationexercise.com/).

> **AI was used at every stage** — from generating test cases and writing automation code, to diagnosing failures and fixing bugs — resulting in **24/24 tests passing (100%)**.

This is a **College Case Study Project** for the subject: *AI in Software Testing*.

---

## 🎯 User Story Under Test

> *"As a customer, I want to login, view products, add a product to cart, and complete checkout so I can successfully place an order."*

---

## 🏗️ Project Structure

```
ai-driven-test-automation-framework/
│
├── AutomationExercise-SeleniumTestNG/          # Main automation project
│   ├── src/
│   │   ├── main/java/pages/                    # Page Object Model classes
│   │   │   ├── HomePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── PaymentPage.java
│   │   │   └── SignupPage.java
│   │   │
│   │   └── test/java/
│   │       ├── base/
│   │       │   └── BaseTest.java               # WebDriver setup, teardown, ExtentReports
│   │       ├── tests/
│   │       │   ├── PositiveTests.java          # 10 positive test cases
│   │       │   ├── NegativeTests.java          # 8 negative test cases
│   │       │   └── EdgeTests.java              # 6 edge test cases
│   │       ├── utils/
│   │       │   └── TestData.java               # Centralized test data
│   │       └── resources/
│   │           └── testng.xml                  # TestNG suite configuration
│   │
│   └── pom.xml                                 # Maven dependencies
│
├── docs/
│   └── AI_Testing_Case_Study_FINAL.docx        # Full project documentation
│
└── README.md
```

---

## 🧪 Test Coverage

| Category | Test Cases | Status |
|---|---|---|
| ✅ Positive Tests (TC_POS) | 10 | All PASS |
| ✅ Negative Tests (TC_NEG) | 8 | All PASS |
| ✅ Edge Tests (TC_EDG) | 6 | All PASS |
| **Total** | **24** | **24/24 PASS** |

### Test Cases at a Glance

**Positive (Happy Path)**
- TC_POS_001 — Successful login with valid credentials
- TC_POS_002 — Navigate to Products page
- TC_POS_003 — Search for a product by name
- TC_POS_004 — View product detail page
- TC_POS_005 — Add product to cart
- TC_POS_006 — Verify cart shows correct quantity
- TC_POS_007 — Logged-in user proceeds to checkout
- TC_POS_008 — Delivery address pre-filled at checkout
- TC_POS_009 — Payment page loads after placing order
- TC_POS_010 — Complete end-to-end purchase flow

**Negative (Error Handling)**
- TC_NEG_001 — Login fails with invalid credentials
- TC_NEG_002 — Login fails with empty email
- TC_NEG_003 — Login fails with empty password
- TC_NEG_004 — Signup fails with already registered email
- TC_NEG_005 — Search returns no results for invalid term
- TC_NEG_006 — Guest user checkout behavior (defect documented)
- TC_NEG_007 — Payment fails with invalid card number
- TC_NEG_008 — Newsletter subscription fails with invalid email

**Edge (Boundary Conditions)**
- TC_EDG_001 — Add maximum quantity (9999) to cart
- TC_EDG_002 — Add minimum quantity (1) to cart
- TC_EDG_003 — Add same product twice, verify totals
- TC_EDG_004 — Remove item from cart, verify empty state
- TC_EDG_005 — Enter very long text in order comment field
- TC_EDG_006 — Direct URL access to /payment page

---

## 🤖 How AI Was Used

### Claude AI (Anthropic)
| Phase | AI Contribution |
|---|---|
| Test Design | Generated all 24 test cases from user story in ~5 minutes |
| Code Generation | Produced full POM architecture, all Page Object classes, and test classes |
| Bug Diagnosis | Analyzed stack traces and identified root causes for 8 failing tests |
| Ad Handling | Designed 3-layer ad interference solution |
| Documentation | Assisted in writing structured case study documentation |

### GitHub Copilot
| Usage | Benefit |
|---|---|
| Inline code completion | Reduced WebDriver boilerplate writing time |
| Locator suggestions | Suggested CSS/XPath selectors from context |
| Method scaffolding | Auto-completed Page Object method signatures |

> **Estimated time saving with AI: ~95%** — what would take 23–33 hours manually was completed in approximately 88 minutes.

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 | Programming language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| TestNG | 7.9.0 | Test framework |
| Maven | 3.x | Build & dependency management |
| WebDriverManager | 5.x | Automatic ChromeDriver management |
| ExtentReports | 5.x | HTML test reporting with screenshots |
| Chrome | 145 | Browser under test |
| Claude AI | Sonnet | Test design, code gen, debugging |
| GitHub Copilot | Latest | Inline code assistance |
| Jira | Cloud | Test tracking & bug management |

---

## ⚙️ How to Run

### Prerequisites
- Java 21 or higher installed
- Maven installed
- Google Chrome browser installed
- Internet connection (tests run against live site)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/ai-driven-test-automation-framework.git
cd ai-driven-test-automation-framework/AutomationExercise-SeleniumTestNG
```

**2. Run the full test suite**
```bash
mvn clean test
```

**3. Run a specific test class**
```bash
mvn clean test -Dtest=PositiveTests
mvn clean test -Dtest=NegativeTests
mvn clean test -Dtest=EdgeTests
```

**4. View the test report**

After the run, open:
```
target/ExtentReports/TestReport.html
```

---

## 🐛 Defects Found

Two real application defects were discovered and logged in Jira:

| Defect | Severity | Description |
|---|---|---|
| Guest Checkout Access | High | Unauthenticated users can access `/checkout` directly without login — security vulnerability |
| Direct Payment Access | Medium | `/payment` page accessible via direct URL without completing checkout flow |

---

## 📁 Documentation

Full project documentation is available in the [`docs/`](./docs/) folder:

- **AI_Testing_Case_Study_FINAL.docx** — Complete case study including user story analysis, all 24 test cases, process flow diagram, AI tool comparison, execution time analysis, Jira evidence, and conclusions.

---

## 🏗️ Design Pattern

This project uses the **Page Object Model (POM)** design pattern:

- Each web page = one Java class in `src/main/java/pages/`
- Page classes contain **element locators** and **interaction methods** only
- Test classes contain **test logic and assertions** only
- `BaseTest.java` handles **WebDriver lifecycle** and **ExtentReports setup**
- `TestData.java` centralizes all **test data constants**

---

## 📊 Jira Project Management

All 24 test cases and 2 defects were tracked in Jira under **AI Testing Case Study Sprint 1**:
- **33 total issues** created and completed
- 1 Epic/User Story + 24 Test Tasks + 8 Bug Reports
- **100% sprint completion**

---

## 👤 Author

**Yash Gondaliya**  
College Project — AI in Software Testing  
February 2026

---

> *"This project demonstrates that AI is not a replacement for QA engineers, but a powerful co-pilot that dramatically accelerates delivery while maintaining quality."*
