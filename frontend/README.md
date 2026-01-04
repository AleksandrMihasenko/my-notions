# Frontend

> React + TypeScript application for my-notions project

**Current Focus**: Basic React UI setup - Learning React, TypeScript, FSD architecture

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Architecture](#architecture)
---

## Overview

Modern React application using TypeScript and Feature-Sliced Design architecture. Built to learn:

- React fundamentals & hooks
- TypeScript in React
- FSD architecture for scalability
- State management (Context API → Zustand)
- Component testing (Jest + React Testing Library)
- Modern CSS (Tailwind CSS)

**Learning Approach**: Start simple, add complexity as project grows

---

## Tech Stack

### Core
- **React**: 19+
- **TypeScript**: 5+
- **Build Tool**: Vite
- **Package Manager**: npm

### Styling
- **Tailwind CSS**: Utility-first CSS
- **PostCSS**: CSS processing

### State Management
- **Redux Toolkit**: App state and user slice

### Routing
- **React Router**: v7+

### i18n
- **i18next**: Locale management and translations

### Testing
- **Jest**: Test runner
- **jsdom**: Browser-like test environment

### Code Quality
- **ESLint**: Linting
- **Stylelint**: SCSS linting
- **TypeScript**: Type checking

---

## Project Structure

Using **Feature-Sliced Design (FSD)** methodology:

```
frontend/
├── src/
│   ├── app/                           # App initialization
│   │   ├── App.tsx
│   │   ├── providers/
│   │   │   ├── Router/
│   │   │   ├── StoreProvider/
│   │   │   ├── ThemeProvider/
│   │   │   └── ErrorBoundary/
│   │   ├── styles/                    # Global styles + themes
│   │   └── types/                     # TS global typings
│   │
│   ├── pages/                         # Route pages
│   │   ├── MainPage/
│   │   ├── NoteListPage/
│   │   ├── ErrorPage/
│   │   └── NotFoundPage/
│   │
│   ├── widgets/                       # UI blocks
│   │   ├── Navbar/
│   │   ├── Sidebar/
│   │   └── ThemeSwitcher/
│   │
│   ├── features/                      # User features
│   │   └── AuthByUsername/
│   │       ├── model/
│   │       └── ui/
│   │
│   ├── entities/                      # Business entities
│   │   └── User/
│   │       └── model/
│   │
│   └── shared/                        # Shared utilities
│       ├── assets/
│       ├── config/
│       │   ├── i18n/
│       │   └── routeConfig/
│       ├── lib/
│       ├── ui/
│       └── index.ts
│
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
└── README.md                          # This file
```

### FSD Layers Explanation

1. **Pages** (`pages/`): Route components, compose widgets & features
2. **Widgets** (`widgets/`): Complex UI blocks (Header, Sidebar)
3. **Features** (`features/`): User actions (login, create workspace)
4. **Entities** (`entities/`): Business logic & data (user, workspace, page)
5. **Shared** (`shared/`): Reusable utilities, UI components, types

**Import Rule**: Higher layers can import from lower layers, not vice versa  
`pages → widgets → features → entities → shared`

---

## Setup & Installation

### Prerequisites

- **Node.js**: 18+ (check: `node -v`)
- **npm**: 9+ (check: `npm -v`)

### 1. Navigate to Frontend

```bash
cd frontend
```

### 2. Install Dependencies

```bash
npm install
```

### 3. Configure Environment (Optional)

Vite is already configured to proxy `/api` to `http://localhost:8081`.
Add a `.env.local` only if you want to override defaults.

---

## Running the Application

### Development Mode

```bash
npm run dev
```

**App runs on**: `http://localhost:5173`

**Features**:
- Hot Module Replacement (HMR)
- Fast refresh
- TypeScript checking in IDE

### Build for Production

```bash
npm run build
```

Output: `dist/` folder

### Preview Production Build

```bash
npm run preview
```

### Linting

```bash
# Lint
npm run lint

# Lint and fix
npm run lint:fix

# Stylelint (SCSS)
npm run stylelint
```

---

## Testing

### Run All Tests

```bash
npm test
```

### Run Tests in Watch Mode

```bash
npm run test:watch
```

### Run Tests with Coverage

```bash
npm run test:coverage
```

View coverage report: `coverage/index.html`

---

## Architecture

### FSD Benefits

✅ **Scalability**: Easy to add features without refactoring  
✅ **Maintainability**: Clear separation, easy to find code  
✅ **Team-friendly**: Multiple devs can work without conflicts  
✅ **Testability**: Isolated features are easier to test
