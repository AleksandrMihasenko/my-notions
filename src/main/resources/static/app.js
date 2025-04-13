document.addEventListener("DOMContentLoaded", function () {
    const notesList = document.getElementById("notesList");
    const saveNoteButton = document.getElementById("saveNoteButton");
    const searchNotes = document.getElementById('searchInput');

    searchNotes.addEventListener('input', () => {
        const query = document.getElementById('searchInput').value.trim();
        loadNotes(query);
    });

    saveNoteButton.addEventListener("click", async () => {
        const title = document.getElementById("noteTitle").value.trim();
        const author = document.getElementById("noteAuthor").value.trim();
        const content = document.getElementById("noteContent").value.trim();

        if (!title || !author || !content) {
            alert("Please fill out all fields.");
            return;
        }

        const noteData = { title, author, content };

        try {
            const response = await fetch("/api/notes", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(noteData),
            });
            if (response.ok) {
                document.getElementById("noteTitle").value = "";
                document.getElementById("noteAuthor").value = "";
                document.getElementById("noteContent").value = "";
                loadNotes();
            } else {
                console.error("Failed to save note");
            }
        } catch (error) {
            console.error("Error saving note:", error);
        }
    });

    async function loadNotes(search = "") {
        notesList.innerHTML = "";
        const url = search ? `/api/notes/search?query=${encodeURIComponent(search)}` : '/api/notes';

        try {
            const response = await fetch(url);
            const notes = await response.json();
            notes.forEach((note) => {
                const noteItem = document.createElement("li");
                noteItem.innerHTML = `
          <h3>${note.title}</h3>
          <p><strong>${note.author}</strong> - ${new Date(note.createdAt).toLocaleString('en-US')}</p>
          <p>${note.content}</p>
          <button class="delete-button" data-id="${note.id}">Delete</button>
        `;
                notesList.appendChild(noteItem);
            });

            document.querySelectorAll(".delete-button").forEach((button) => {
                button.addEventListener("click", async () => {
                    const noteId = button.getAttribute("data-id");
                    await deleteNote(noteId);
                    loadNotes();
                });
            });
        } catch (error) {
            console.error("Error loading notes:", error);
        }
    }

    async function deleteNote(id) {
        try {
            const response = await fetch(`/api/notes/${id}`, { method: "DELETE" });
            if (!response.ok) {
                console.error("Failed to delete note");
            }
        } catch (error) {
            console.error("Error deleting note:", error);
        }
    }

    const createTagButton = document.getElementById("createTagButton");
    const tagsList = document.getElementById("tagsList");
    const newTagNameInput = document.getElementById("newTagName");

    createTagButton.addEventListener("click", async () => {
        const name = newTagNameInput.value.trim();
        if (!name) {
            alert("Please enter a tag name.");
            return;
        }

        try {
            const response = await fetch("/tags", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name }),
            });

            if (response.ok) {
                loadTags();
                newTagNameInput.value = "";
            } else {
                console.error("Failed to create tag:", response.status);
            }
        } catch (error) {
            console.error("Error creating tag:", error);
        }
    });

    async function loadTags() {
        tagsList.innerHTML = "";
        try {
            const response = await fetch("/tags");
            const tags = await response.json();
            tags?.forEach((tag) => {
                const li = document.createElement("li");
                li.innerHTML = `
            <span>${tag.name}</span>
            <button class="delete-tag-button" data-id="${tag.id}">Delete</button>
          `;
                tagsList.appendChild(li);
            });

            document.querySelectorAll(".delete-tag-button").forEach((button) => {
                button.addEventListener("click", async () => {
                    const tagId = button.getAttribute("data-id");
                    await deleteTag(tagId);
                    loadTags();
                });
            });
        } catch (error) {
            console.error("Error loading tags:", error);
        }
    }

    async function deleteTag(id) {
        try {
            const response = await fetch(`/tags/${id}`, { method: "DELETE" });
            if (!response.ok) {
                console.error("Failed to delete tag:", response.status);
            }
        } catch (error) {
            console.error("Error deleting tag:", error);
        }
    }

    loadNotes();
    loadTags();
});
