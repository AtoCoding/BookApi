document.addEventListener("DOMContentLoaded", async function () {
    const currentPath = window.location.pathname;
    if (currentPath.endsWith("homepage.html")) { // Kiểm tra nếu đường dẫn kết thúc bằng 'homepage.html'
        await getBookList();
    } else if (currentPath.endsWith("create-new-book.html")) {
        let isLogined = false;
        isLogined = await checkAuthentication();
        if (isLogined === false) {
            window.location.assign("login.html");
        } else {
            await getCategoryList();
        }
    } else if (currentPath.endsWith("book-details.html")) {
        let isLogined = false;
        isLogined = await checkAuthentication();
        if (isLogined === false) {
            window.location.assign("login.html");
        } else {
            await displayBookDetails();
        }
    }
});
async function getBookList() {
    const apiUrl = "/bookmanagement/api/v1/book/all";
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
            formatDateCreated: response.data.data.formatDateCreated,
            quantity: response.data.data.quantity,
            categoryList: JSON.stringify(response.data.data.categoryList)
        };
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
    const params = new URLSearchParams(window.location.search);
    const bookId = params.get("bookId");
    const bookName = params.get("bookName");
    const author = params.get("author");
    const formatDateCreated = params.get("formatDateCreated");
    const quantity = params.get("quantity");
    const categoryListString = params.get("categoryList");
    const categoryList = categoryListString ? JSON.parse(categoryListString) : [];

    const cellBookId = document.getElementById("book-id");
    cellBookId.textContent = bookId;

    const cellBookName = document.getElementById("book-name");
    cellBookName.value = bookName;

    const cellAuthor = document.getElementById("author");
    cellAuthor.value = author;

    const cellDateCreated = document.getElementById("date-created");
    cellDateCreated.value = formatDateCreated;

    const cellQuantity = document.getElementById("quantity");
    cellQuantity.value = quantity;

    for (let i = 0; i < categoryList.length; i++) {
        const cellCategories = document.getElementById("categories");
        const optionCategory = document.createElement("option");
        optionCategory.value = categoryList[i].categoryId;
        optionCategory.textContent = categoryList[i].categoryName;
        optionCategory.setAttribute("selected", "true");
        cellCategories.appendChild(optionCategory);
    }

    const cellUpdateButton = document.getElementById("update-button");
    cellUpdateButton.setAttribute("onclick", "modifyData('update', " + bookId + ")");

    const cellDeleteButton = document.getElementById("delete-button");
    cellDeleteButton.setAttribute("onclick", "modifyData('delete', " + bookId + ")");
}

async function modifyData(action, bookId) {
    if (action === "update") {
        const cellBookName = document.getElementById("book-name");
        cellBookName.removeAttribute("disabled");

        const cellAuthor = document.getElementById("author");
        cellAuthor.removeAttribute("disabled");

        const cellDateCreated = document.getElementById("date-created");
        cellDateCreated.removeAttribute("disabled");

        const cellQuantity = document.getElementById("quantity");
        cellQuantity.removeAttribute("disabled");

        const cellCategories = document.getElementById("categories");
        cellCategories.removeAttribute("disabled");

        const txtUpdate = document.getElementById("txtUpdate");
        txtUpdate.textContent = "Confirm update";
        const btnUpdate = document.getElementById("update-button");
        btnUpdate.setAttribute("onclick", "confirmModifyData('update', " + bookId + ")");

        try {
            const categoryResponse = await getCategoryResponse();
            const categoryData = categoryResponse.data.data;

            const cellCategories = document.getElementById("categories");
            for (let i = 0; i < categoryData.length; i++) {
                const option = document.createElement("option");
                const optionSelectedValue = cellCategories.value;
                option.value = categoryData[i].categoryId;
                option.textContent = categoryData[i].categoryName;
                cellCategories.appendChild(option);
            }

        } catch (error) {
            alert(error);
        }
    } else if (action === "delete") {

    }
}

async function confirmModifyData(action, bookId) {
    if (action === "update") {
        const cellBookId = document.getElementById("book-id").textContent;
        const cellBookName = document.getElementById("book-name").value;
        const cellAuthor = document.getElementById("author").value;
        const cellDateCreated = document.getElementById("date-created").value;
        const cellQuantity = document.getElementById("quantity").value;
        const selectedCategories = Array.from(document.querySelector('#categories').selectedOptions)
                .map(option => ({
                        categoryId: option.value,
                        categoryName: option.textContent.trim()
                    }));
        const apiUrl = "/bookmanagement/api/v1/admin/book/update";
        
        try {
            const response = await axios.put(apiUrl, {
                bookId: cellBookId,
                bookName: cellBookName,
                author: cellAuthor,
                dateCreated: cellDateCreated,
                quantity: cellQuantity,
                categoryList: selectedCategories
            });
            console.log(response);
            if (response !== null) {
                alert(response.data.message);
                
                //window.location.assign("homepage.html");
            }
        } catch (error) {
            if (error.response && error.response.status === 401) {
                window.location.assign("login.html");
            } else {
                window.location.assign("homepage.html");
            }
        }
    } else if (action === "delete") {

    }
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