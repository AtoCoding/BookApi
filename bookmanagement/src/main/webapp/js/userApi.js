async function checkAuthentication() {
    const apiUrl = "/bookmanagement/api/v1/auth";
    
    try {
        const response = await axios.get(apiUrl);
        console.log(response);
        return response.data.isLogined;
    } catch(error) {
        console.log(error);
        return false;
    }
}