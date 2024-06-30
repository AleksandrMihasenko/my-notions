import Navbar from 'widgets/Navbar/ui/Navbar';
import { useTheme } from 'app/providers/ThemeProvider';
import { AppRouter } from 'app/providers/router';
import { getClassNames } from 'shared/lib/classNames/classNames';
import './styles/index.scss';

function App() {
    const {theme, toggleTheme} = useTheme();

    return (
        <div className={getClassNames('app', {}, [theme])}>
            <button onClick={toggleTheme}>Toggle</button>

            <Navbar />

            <AppRouter />
        </div>
    );
}

export default App;