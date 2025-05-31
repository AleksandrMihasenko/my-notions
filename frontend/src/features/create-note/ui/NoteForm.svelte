<script lang="ts">
	import { createNote } from '@/entities/note/api/notesApi';
	export let onSuccess: () => void;

	let title = '', content = '', author = '';

	async function submit() {
		await createNote({ title, content, author });
		title = content = author = '';
		onSuccess?.();
	}
</script>

<form on:submit|preventDefault={submit}>
	<input placeholder="Title" bind:value={title} />
	<input placeholder="Author" bind:value={author} />
	<textarea placeholder="Content" bind:value={content} />
	<button type="submit">Add Note</button>
</form>
