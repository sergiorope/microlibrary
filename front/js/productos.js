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


                const card = document.createElement('div');
                card.className = 'card mb-3 card-size';

                const buyButton = document.createElement('a');
                buyButton.className = "btn btn-primary";
                buyButton.textContent = "Comprar";

                const cardBody = document.createElement('div');
                cardBody.className = 'card-body';
                
                const nameElement = document.createElement('h5');
                nameElement.className = 'product-name';
                nameElement.textContent = item.name; 

                const descriptionElement = document.createElement('p');
                descriptionElement.className = 'product-description';
                descriptionElement.textContent = item.description;

                const imageElement = document.createElement('img');
                imageElement.className = 'product-image';
                imageElement.src = item.image;
                imageElement.alt = item.name;


                
                cardBody.appendChild(imageElement);
                cardBody.appendChild(nameElement);
                cardBody.appendChild(descriptionElement);
                cardBody.appendChild(buyButton);

                card.appendChild(cardBody);

                resultContainer.appendChild(card);
                
            });
        })
        .catch(error => {
            console.error('Hubo un problema con la petición Fetch:', error);
            document.getElementById('result').textContent = 'Error: ' + error.message;
        });
});
