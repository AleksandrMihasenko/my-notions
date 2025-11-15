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
- **React**: 18+
- **TypeScript**: 5+
- **Build Tool**: Vite
- **Package Manager**: npm

### Styling
- **Tailwind CSS**: Utility-first CSS
- **PostCSS**: CSS processing

### State Management
- **Context API**: Initial state (Month 1-2)
- **Zustand**: Complex state (Month 3+ if needed)

### Routing
- **React Router**: v6+

### Forms & Validation
- **React Hook Form**: Form handling
- **Zod**: Schema validation (maybe)

### HTTP Client
- **Axios**: API requests
- **TanStack Query**: Server state (Month 3+ if needed)

### Testing
- **Jest**: Test runner
- **React Testing Library**: Component tests
- **MSW**: API mocking (Mock Service Worker)

### Code Quality
- **ESLint**: Linting
- **Prettier**: Code formatting
- **TypeScript**: Type checking

---

## Project Structure

Using **Feature-Sliced Design (FSD)** methodology:

```
frontend/
├── src/
│   ├── app/                           # Application initialization
│   │   ├── App.tsx                    # Main app component
│   │   ├── main.tsx                   # Entry point
│   │   ├── providers/                 # Global providers
│   │   │   ├── AuthProvider.tsx       # Auth context
│   │   │   └── RouterProvider.tsx     # Router setup
│   │   └── styles/
│   │       └── index.css              # Global styles + Tailwind
│   │
│   ├── pages/                         # Page components (FSD layer 1)
│   │   ├── LoginPage/
│   │   │   ├── ui/
│   │   │   │   └── LoginPage.tsx
│   │   │   └── index.ts
│   │   ├── RegisterPage/
│   │   ├── DashboardPage/
│   │   ├── WorkspacePage/
│   │   └── PageEditorPage/
│   │
│   ├── widgets/                       # Complex UI blocks (FSD layer 2)
│   │   ├── Header/
│   │   │   ├── ui/
│   │   │   │   └── Header.tsx
│   │   │   └── index.ts
│   │   ├── Sidebar/
│   │   ├── PageTree/                  # Pages hierarchy
│   │   └── EditorToolbar/
│   │
│   ├── features/                      # User features (FSD layer 3)
│   │   ├── auth/
│   │   │   ├── login/
│   │   │   │   ├── ui/
│   │   │   │   │   └── LoginForm.tsx
│   │   │   │   ├── model/
│   │   │   │   │   └── useLogin.ts    # Hook for login logic
│   │   │   │   └── index.ts
│   │   │   └── register/
│   │   │
│   │   ├── workspace/
│   │   │   ├── create/
│   │   │   ├── edit/
│   │   │   └── delete/
│   │   │
│   │   ├── page/
│   │   │   ├── create/
│   │   │   ├── edit/
│   │   │   └── delete/
│   │   │
│   │   └── editor/                    # Rich text editing
│   │       └── basic/
│   │
│   ├── entities/                      # Business entities (FSD layer 4)
│   │   ├── user/
│   │   │   ├── model/
│   │   │   │   ├── types.ts           # User type
│   │   │   │   └── store.ts           # User state (if needed)
│   │   │   ├── api/
│   │   │   │   └── userApi.ts         # User API calls
│   │   │   └── index.ts
│   │   │
│   │   ├── workspace/
│   │   │   ├── model/
│   │   │   │   └── types.ts
│   │   │   ├── api/
│   │   │   │   └── workspaceApi.ts
│   │   │   ├── ui/
│   │   │   │   └── WorkspaceCard.tsx  # Reusable workspace component
│   │   │   └── index.ts
│   │   │
│   │   └── page/
│   │       ├── model/
│   │       ├── api/
│   │       └── ui/
│   │
│   └── shared/                        # Shared utilities (FSD layer 5)
│       ├── api/
│       │   ├── axios.ts               # Axios instance
│       │   └── interceptors.ts        # JWT interceptor
│       │
│       ├── lib/                       # Utilities
│       │   ├── hooks/
│       │   │   ├── useAuth.ts         # Auth hook
│       │   │   └── useDebounce.ts     # Utility hooks
│       │   ├── utils/
│       │   │   ├── date.ts            # Date formatting
│       │   │   └── validation.ts      # Validators
│       │   └── constants/
│       │       └── routes.ts          # Route constants
│       │
│       ├── ui/                        # Shared UI components
│       │   ├── Button/
│       │   │   ├── Button.tsx
│       │   │   ├── Button.test.tsx
│       │   │   └── index.ts
│       │   ├── Input/
│       │   ├── Modal/
│       │   ├── Spinner/
│       │   └── Card/
│       │
│       └── types/
│           └── common.ts              # Common types
│
├── public/                            # Static assets
│   └── logo.svg
│
├── tests/                             # Test utilities
│   ├── setup.ts
│   └── mocks/
│       └── handlers.ts                # MSW handlers
│
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
├── tailwind.config.js
├── .eslintrc.json
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

### 3. Configure Environment

Create `.env.local`:

```env
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=my-notions
```

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

### Linting & Formatting

```bash
# Lint
npm run lint

# Lint and fix
npm run lint:fix

# Format with Prettier
npm run format
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
