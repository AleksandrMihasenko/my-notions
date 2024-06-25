import { Suspense } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import { MainPageAsync } from './pages/MainPage/MainPage.async';
import { AboutPageAsync } from './pages/AboutPage/AboutPage.async';
import { useTheme } from './theme/useTheme';
import { getClassNames } from './helpers/classNames/classNames';
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
                    <Route path="/" element={<MainPageAsync />} />
                    <Route path="/about" element={<AboutPageAsync />} />
                </Routes>
            </Suspense>
        </div>
    );
}

export default App;