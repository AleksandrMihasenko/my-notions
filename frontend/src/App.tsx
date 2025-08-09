import { Routes, Route, Link } from 'react-router-dom';
import NoteListPage from '@/pages/NoteListPage/NoteListPage'
import MainPage from '@/pages/MainPage/MainPage';
import { Suspense } from 'react';
import '@/styles/index.scss'
import { useTheme } from '@/theme/useTheme';
import { classNames } from '@/helpers/classNames/classNames';

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
