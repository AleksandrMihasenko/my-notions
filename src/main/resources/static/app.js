document.addEventListener("DOMContentLoaded", function () {
    const notesList = document.getElementById("notesList");
    const saveNoteButton = document.getElementById("saveNoteButton");

    saveNoteButton.addEventListener("click", async () => {
        const title = document.getElementById("noteTitle").value.trim();
        const author = document.getElementById("noteAuthor").value.trim();
        const content = document.getElementById("noteContent").value.trim();

        if (!title || !author || !content) {
            alert("Please fill out all fields.");
            return;
        }

        const creationDate = new Date().toISOString();
        const noteData = { title, author, content, creationDate };

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

    async function loadNotes() {
        notesList.innerHTML = "";
        try {
            const response = await fetch("/api/notes");
            const notes = await response.json();
            notes.forEach((note) => {
                const noteItem = document.createElement("li");
                noteItem.innerHTML = `
          <h3>${note.title}</h3>
          <p><strong>${note.author}</strong> - ${new Date(note.creationDate).toLocaleString()}</p>
          <p>${note.content}</p>
          <button class="delete-button" data-id="${note.id}">Delete</button>
        `;
                notesList.appendChild(noteItem);
            });

            // Добавляем обработчики удаления после рендера
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

    loadNotes();
});