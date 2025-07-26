import { Routes, Route, Link } from 'react-router-dom';
import NoteListPage from '@/pages/NoteListPage/NoteListPage.tsx'
import MainPage from '@/pages/MainPage/MainPage.tsx';
import { Suspense } from 'react';

export const App = () => {
	return (
		<div className="app">
			<Link to={"/"}>Main</Link>
			<Link to={"/notes"}>Notes List</Link>

			<Suspense fallback={<div>Loading...</div>}>
				<Routes>
					<Route path="/" element={<MainPage />} />
					<Route path="/notes" element={<NoteListPage />} />
				</Routes>
			</Suspense>
		</div>
	)
}
