import { Link } from 'react-router-dom';
import './styles/index.scss'
import { useTheme } from '@/app/providers/ThemeProvider';
import { classNames } from '@/shared';
import { AppRouter } from '@/app/providers/Router';

export const App = () => {
	const { theme, toggleTheme } = useTheme();

	return (
		<div className={classNames('app', {}, [theme as string])}>
			<Link to={"/"}>Main</Link>
			<Link to={"/notes"}>Notes List</Link>
			<button onClick={toggleTheme}>Toggle theme</button>

			<AppRouter />
		</div>
	)
}
