import { Routes, Route, Link } from 'react-router-dom';
import NoteListPage from '@/pages/NoteListPage/NoteListPage.tsx'
import MainPage from '@/pages/MainPage/MainPage.tsx';
import { Suspense } from 'react';
import '@/styles/index.scss'
import { useTheme } from '@/theme/useTheme.ts';

export const App = () => {
	const { theme, toggleTheme } = useTheme();

	return (
		<div className={`app ${theme}`}>
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
