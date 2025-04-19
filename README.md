# 🧠 Smart Meeting Manager

A full-stack web application for managing meetings and attendees. Built with **Spring Boot (backend)** and **Angular (frontend)**, it includes advanced features like **AI-powered meeting insights using Mistral via Ollama**, **daily.co meeting room generator**, and **PDF/Excel export** support.

---

## 🚀 Features

### 📅 Core Modules
- **Meetings CRUD** – Create, update, delete, and list meetings
- **Attendees CRUD** – Manage meeting participants

### 📊 Analytics & AI
- **Integrated with Mistral (via Ollama)** – Ask natural language questions about meetings and attendees
- **Meeting statistics** – Query duration, location, frequency, and participation
- **Basic French/English query handling**

### 📤 Export & Sharing
- Export meeting data as **PDF** or **Excel**
- **Daily.co** meeting room generator for virtual rooms

---

## 🧠 Mistral AI Integration

The backend connects to **Mistral** via **Ollama** running locally to provide smart insights into meeting data.

### Example Prompts:
- “What meetings took place at Paris?”
- “Frequency of safety meetings?”
- “Meeting about project alpha?”

> ⚠️ **Note:** Mistral runs locally via Ollama and answers are based solely on the question prompt. It doesn’t retain conversation context or support deep personalization.

---

## 🛠️ Tech Stack

### Backend – Spring Boot
- Java 17+
- REST APIs with Spring Web
- Custom service for Mistral/Ollama integration
- Repositories for querying statistics

### Frontend – Angular
- TypeScript + Angular 15+
- Responsive UI for meetings and attendees
- PDF/Excel export buttons
- Chat interface for AI queries

---

## 📦 Installation & Run

### ⚙️ Backend

1. **Start Ollama with Mistral**:
   ```bash
   ollama run mistral
   
Run Spring Boot:
./mvnw spring-boot:run

API Endpoint Example:
POST /api/chat/ask – Send prompt to Mistral

🌐 Frontend
Navigate to frontend/

Install dependencies:
npm install

Run the Angular app:
ng serve

🧪 Example API Call

POST /api/chat/ask
question: List meetings about planning at Berlin
Response:
answer: Meetings at Berlin:\n- Sprint Planning on 2024-04-10 (Planning)

📁 Project Structure

/backend
  └── /controller
  └── /service
  └── /Entities
  └── /repository
/frontend
  └── /components
  └── /services
  └── /models
  
📌 Known Limitations
Mistral does not support memory/personalization – every question is treated statelessly.

Ollama must be running locally on port 11434.

📄 License
This project is licensed under the MIT License.

✨ Credits
Developed by Nada Louhichi as part of a school project.
AI insights powered by Mistral and Ollama
Video room generation via daily.co

