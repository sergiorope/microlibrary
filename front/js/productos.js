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

            const resultContainer = document.getElementById('result');
            resultContainer.innerHTML = '';

            
            data.forEach(item => {
                
                const nameElement = document.createElement('span');
                nameElement.className = 'product-name';
                nameElement.textContent = item.name; 

                const descriptionElement = document.createElement('span');
                descriptionElement.className = 'product-description';
                descriptionElement.textContent = item.description;

                const imageElement = document.createElement('img');
                imageElement.className = 'product-image';
                imageElement.src = item.image;
           

                resultContainer.appendChild(nameElement);
                resultContainer.appendChild(document.createElement('br')); 
                resultContainer.appendChild(descriptionElement);
                resultContainer.appendChild(document.createElement('br')); 
                resultContainer.appendChild(imageElement);
                resultContainer.appendChild(document.createElement('br')); 
                
            });
        })
        .catch(error => {
            console.error('Hubo un problema con la petición Fetch:', error);
            document.getElementById('result').textContent = 'Error: ' + error.message;
        });
});
