// Функция для отображения уведомления
function showAlert(message) {
    alert(message);
}

// Функция для отправки данных формы через fetch
async function submitForm(formId, url, method, successRedirectUrl) {
    const form = document.getElementById(formId);
    const formData = new FormData(form);
    const data = {};

    formData.forEach((value, key) => {
        data[key] = value;
    });

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Данные успешно отправлены!');
            window.location.href = successRedirectUrl || 'index.html'; // Перенаправление на заданную страницу или главную
        } else {
            showAlert('Произошла ошибка при отправке данных.');
        }
    } catch (error) {
        console.error('Ошибка при отправке данных:', error);
        showAlert('Произошла ошибка при отправке данных.');
    }
}

// Загрузка списка книг
async function loadBooks() {
    try {
        const response = await fetch('/books');
        const books = await response.json();
        const bookList = document.getElementById("book-list");

        books.forEach(book => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${book.title}</td>
                <td>${book.author.name}</td>
                <td>${book.isbn}</td>
                <td>${book.copiesAvailable}</td>
                <td><a href="edit_book.html?id=${book.id}">Редактировать</a></td>
            `;
            bookList.appendChild(row);
        });
    } catch (error) {
        console.error('Ошибка при загрузке книг:', error);
        showAlert('Не удалось загрузить книги.');
    }
}

// Обработка событий при загрузке страницы
document.addEventListener("DOMContentLoaded", () => {
    // Загрузка книг для index.html
    if (document.getElementById("book-list")) {
        loadBooks().catch(error => console.error('Ошибка при вызове loadBooks:', error));
    }

    // Обработка формы редактирования книги
    const editForm = document.getElementById("edit-book-form");
    if (editForm) {
        const bookId = new URLSearchParams(window.location.search).get("id");
        const editUrl = `/books/${bookId}`;

        editForm.addEventListener("submit", (event) => {
            event.preventDefault();
            submitForm("edit-book-form", editUrl, "PUT").catch(error => console.error('Ошибка при редактировании книги:', error));
        });
    }

    // Обработка формы возврата книги
    const returnForm = document.getElementById("return-book-form");
    if (returnForm) {
        returnForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const bookId = returnForm.bookId.value;

            fetch(`/books/${bookId}`, {
                method: "DELETE"
            }).then(response => {
                if (response.ok) {
                    showAlert("Книга возвращена успешно!");
                    window.location.href = "return_success.html"; // Перенаправление на страницу успешного возврата
                } else {
                    showAlert("Ошибка при возврате книги.");
                }
            }).catch(error => console.error('Ошибка при возврате книги:', error));
        });
    }

    // Обработка формы регистрации
    const registrationForm = document.getElementById("registration-form");
    if (registrationForm) {
        registrationForm.addEventListener("submit", (event) => {
            event.preventDefault();
            submitForm("registration-form", "/register", "POST", "register_success.html").catch(error => console.error('Ошибка при регистрации:', error));
        });
    }

    // Пример обработки формы входа
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.addEventListener("submit", (event) => {
            event.preventDefault();
            submitForm("login-form", "/login", "POST", "login_success.html").catch(error => console.error('Ошибка при входе:', error));
        });
    }
});
