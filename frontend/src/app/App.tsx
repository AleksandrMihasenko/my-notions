import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { NoteListPage } from '@/pages/NoteListPage'

export const App = () => {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<NoteListPage />} />
			</Routes>
		</BrowserRouter>
	)
}
