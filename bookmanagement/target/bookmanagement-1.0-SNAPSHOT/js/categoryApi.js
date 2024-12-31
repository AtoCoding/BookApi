async function getCategoryList() {
    const apiUrl = "/bookmanagement/api/v1/category";

    try {
        const response = await axios.get(apiUrl);
        const categoryList = response.data.data;

        if (response !== null) {
            const optCategoryName = document.getElementById("categories");
            for (let i = 0; i < categoryList.length; i++) {
                const option = document.createElement("option");
                const optionSelectedValue = optCategoryName.value;
                option.value = categoryList[i].categoryId;
                option.textContent = categoryList[i].categoryName;
                optCategoryName.appendChild(option);
            }
        }
    } catch (error) {
        console.log(error);
        alert(error);
    }
}

async function getCategoryResponse() {
    const apiUrl = "/bookmanagement/api/v1/category";
    try {
        const response = await axios.get(apiUrl);
        return response;
    } catch (error) {
        alert(error);
        console.log(error);
    }
}