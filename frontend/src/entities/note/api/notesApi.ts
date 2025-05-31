import { baseFetch } from '@/shared/api/baseFetch';

export const fetchNotes = () => baseFetch('/api/notes');
export const createNote = (note: { title: string; content: string; author: string }) =>
	baseFetch('/api/notes', {
		method: 'POST',
		body: JSON.stringify(note),
	});
