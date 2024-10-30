document.addEventListener('DOMContentLoaded', () => {
    const apiUrl = 'http://localhost:8080/product/list'; 

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la red');
            }
            return response.json(); 
        })
        .then(data => {
            console.log(data);
            document.getElementById('result').textContent = JSON.stringify(data, null, 2); 
        })
        .catch(error => {
            console.error('Hubo un problema con la petición Fetch:', error);
            document.getElementById('result').textContent = 'Error: ' + error.message;
        });
});
