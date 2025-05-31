<script lang="ts">
	import { fetchNotes } from '@/entities/note/api/notesApi';
	import NoteForm from '@/features/create-note/ui/NoteForm.svelte';

	let notes = [];

	async function loadNotes() {
		notes = await fetchNotes();
	}

	loadNotes();
</script>

<NoteForm onSuccess={loadNotes} />

<ul>
	{#each notes as note}
		<li>
			<strong>{note.title}</strong> – {note.content} by {note.author}
		</li>
	{/each}
</ul>
