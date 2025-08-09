import { Routes, Route, Link } from 'react-router-dom';
import { MainPage, NoteListPage } from '@/pages'
import { Suspense } from 'react';
import './styles/index.scss'
import { useTheme } from '@/app/providers/ThemeProvider';
import { classNames } from '@/shared';

export const App = () => {
	const { theme, toggleTheme } = useTheme();

	return (
		<div className={classNames('app', {}, [theme as string])}>
			<Link to={"/"}>Main</Link>
			<Link to={"/notes"}>Notes List</Link>
			<button onClick={toggleTheme}>Toggle theme</button>

			<Suspense fallback={<div>Loading...</div>}>
				<Routes>
					<Route path="/" element={<MainPage />} />
					<Route path="/notes" element={<NoteListPage />} />
				</Routes>
			</Suspense>
		</div>
	)
}
