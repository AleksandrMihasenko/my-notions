import { Suspense } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import { MainPage } from 'pages/MainPage';
import { AboutPage } from 'pages/AboutPage';
import { useTheme } from 'app/providers/ThemeProvider';
import { getClassNames } from 'shared/lib/classNames/classNames';
import './styles/index.scss';

function App() {
    const {theme, toggleTheme} = useTheme();

    return (
        <div className={getClassNames('app', {}, [theme])}>
            <button onClick={toggleTheme}>Toggle</button>

            <Link to={'/'}>Main</Link>
            <Link to={'/about'}>About</Link>

            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<MainPage />} />
                    <Route path="/about" element={<AboutPage />} />
                </Routes>
            </Suspense>
        </div>
    );
}

export default App;