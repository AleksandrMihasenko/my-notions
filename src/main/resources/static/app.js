document.addEventListener("DOMContentLoaded", function() {
    loadTodos();

    document.getElementById('todoForm').addEventListener('submit', async function(event) {
        event.preventDefault();
        const task = document.getElementById('taskInput').value.trim();
        if (!task) return alert("Add task");

        await fetch('/api/todos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ task, completed: false })
        });

        document.getElementById('todoForm').reset();
        loadTodos();
    });
});

async function loadTodos() {
    const response = await fetch('/api/todos');
    const todos = await response.json();
    const list = document.getElementById('todoList');
    list.innerHTML = '';

    todos.forEach(todo => {
        const li = document.createElement('li');
        li.innerHTML = `
            <input type="checkbox" ${todo.completed ? 'checked' : ''} onchange="updateTodo(${todo.id}, this.checked)">
            ${todo.task}
            <button onclick="deleteTodo(${todo.id})">Удалить</button>
        `;
        list.appendChild(li);
    });
}

async function deleteTodo(id) {
    await fetch(`/api/todos/${id}`, { method: 'DELETE' });
    loadTodos();
}

async function updateTodo(id, completed) {
    await fetch(`/api/todos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ completed })
    });
    loadTodos();
}
