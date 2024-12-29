document.addEventListener("DOMContentLoaded", async function () {
    const currentPath = window.location.pathname;
    if (currentPath.endsWith("homepage.html")) { // Kiểm tra nếu đường dẫn kết thúc bằng 'homepage.html'
        await getBookList();
    } else if (currentPath.endsWith("create-new-book.html")) {
        let isLogined = false;
        isLogined = await checkAuthentication();
        if(isLogined === false) {
            window.location.assign("login.html");
        } else {
            await getCategoryList();
        }
    } else if (currentPath.endsWith("book-details.html")) {
        let isLogined = false;
        isLogined = await checkAuthentication();
        if(isLogined === false) {
            window.location.assign("login.html");
        } else {
            await displayBookDetails();
        }
    }
});
async function getBookList() {
    const apiUrl = "/bookmanagement/api/v1/book";
    try {
        const response = await axios.get(apiUrl);
        console.log(response);
//        console.log(response.data);
//        console.log(response.data.data);
//        console.log(response.data.data.length);

        const tbody = document.getElementById('bodyTable'); // Lấy phần <tbody>

        for (let i = 0; i < response.data.data.length; i++) {
            //console.log(response.data.data[i].name);

            // Tạo một dòng mới (tr)
            const row = document.createElement('tr');
            row.setAttribute("onclick", "getBookDetails(" + response.data.data[i].bookId + ")");
            // Tạo các ô dữ liệu (td)
            const cellId = document.createElement('td');
            cellId.textContent = response.data.data[i].bookId; // Gán giá trị ID

            const cellName = document.createElement('td');
            cellName.textContent = response.data.data[i].bookName; // Gán giá trị Name

            const cellAuthor = document.createElement('td');
            cellAuthor.textContent = response.data.data[i].author; // Gán giá trị Author

            // Thêm các ô vào dòng
            row.appendChild(cellId);
            row.appendChild(cellName);
            row.appendChild(cellAuthor);
            // Thêm dòng vào bảng
            tbody.appendChild(row);
        }
    } catch (error) {
        console.log(error);
    }
}

async function getBookDetails(bookId) {
    const apiUrl = "/bookmanagement/api/v1/admin/book/details?bookId=" + bookId;
    try {
        const response = await axios.get(apiUrl);
        const bookDetails = {
            bookId: response.data.data.bookId,
            bookName: response.data.data.bookName,
            author: response.data.data.author,
            dateCreated: response.data.data.dateCreated,
            quantity: response.data.data.quantity,
            categoryList: JSON.stringify(response.data.data.categoryList)
        };
        console.log(bookDetails);
        const queryString = new URLSearchParams(bookDetails).toString();
        window.location.assign("book-details.html?" + queryString);
    } catch (error) {
        if (error.response && error.response.status === 401) {
            window.location.assign("login.html");
        } else {
            window.location.assign("homepage.html");
        }
    }
}

async function displayBookDetails() {
    console.log("Loading");
    const params = new URLSearchParams(window.location.search);
    const bookId = params.get("bookId");
    const bookName = params.get("bookName");
    const author = params.get("author");
    const dateCreated = params.get("dateCreated");
    const quantity = params.get("quantity");
    const categoryListString = params.get("categoryList");
    const categoryList = categoryListString ? JSON.parse(categoryListString) : [];
    console.log(bookId);
    console.log(bookName);
    console.log(author);
    console.log(dateCreated);
    console.log(quantity);
    console.log(categoryList);
}

async function navigateToCreateBook() {
    window.location.assign("create-new-book.html");
}

async function createNewBook() {
    const txtBookName = document.getElementById("txtBookName").value;
    const txtAuthor = document.getElementById("txtAuthor").value;
    const dtDateCreated = document.getElementById("dtDateCreated").value;
    const nbQuantity = document.getElementById("nbQuantity").value;
    const selectedCategories = Array.from(document.querySelector('#categories').selectedOptions)
            .map(option => ({
                    categoryId: option.value, // Giá trị của option
                    categoryName: option.textContent.trim() // Tên hiển thị của option
                }));
    const apiUrl = "/bookmanagement/api/v1/admin/book/create";
    try {
        const response = await axios.post(apiUrl, {
            bookName: txtBookName,
            author: txtAuthor,
            dateCreated: dtDateCreated,
            quantity: nbQuantity,
            categoryList: selectedCategories
        });
        if (response !== null) {
            alert(response.data.message);
            window.location.assign("homepage.html");
        }
    } catch (error) {
        if (error.response && error.response.status === 401) {
            window.location.assign("login.html");
        } else {
            window.location.assign("homepage.html");
        }
    }
}