import { Link } from 'react-router-dom';
import './styles/index.scss'
import { useTheme } from '@/app/providers/ThemeProvider';
import { classNames } from '@/shared';
import { AppRouter } from '@/app/providers/Router';
import { Navbar } from '@/widgets/Navbar';

export const App = () => {
	const { theme, toggleTheme } = useTheme();

	return (
		<div className={classNames('app', {}, [theme as string])}>
			<Navbar />

			<button onClick={toggleTheme}>Toggle theme</button>

			<AppRouter />
		</div>
	)
}
