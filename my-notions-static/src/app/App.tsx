import { Suspense } from 'react';
import Navbar from 'widgets/Navbar/ui/Navbar';
import { useTheme } from 'app/providers/ThemeProvider';
import { AppRouter } from 'app/providers/router';
import { Sidebar } from 'widgets/Sidebar';
import { getClassNames } from 'shared/lib/classNames/classNames';
import './styles/index.scss';

function App() {
    const {theme} = useTheme();

    return (
        <div className={getClassNames('app', {}, [theme])}>
            <Suspense fallback={null}>
                <Navbar />

                <div className="content-page">
                    <Sidebar />

                    <AppRouter />
                </div>
            </Suspense>
        </div>
    );
}

export default App;