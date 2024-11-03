document.addEventListener('DOMContentLoaded', () => {
    
    const apiUrlGET = 'http://localhost:8080/customer/list'; 
    

    //Petición para recuperar todos los clientes al cargar la página
    fetch(apiUrlGET)
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
                card.className = 'card mb-2 w-100'; 



                const updateButton = document.createElement('a');
                updateButton.className = "btn btn-primary";
                updateButton.textContent = "Editar";
                updateButton.href ="../putcustomer.html"

                const deleteButton = document.createElement('a');
                deleteButton.className = "btn btn-danger boton-eliminar";
                deleteButton.textContent = "Eliminar";

                const cardBody = document.createElement('div');
                cardBody.className = 'card-body d-flex justify-content-between align-items-center'; 
                
                const nameElement = document.createElement('strong');
                nameElement.className = 'customer-name';
                nameElement.textContent = item.name; 

                

                const surnameElement = document.createElement('strong');
                surnameElement.className = 'customer-surname ms-2';
                surnameElement.textContent = item.surname; 

           
                cardBody.appendChild(nameElement);
                cardBody.appendChild(surnameElement);
                cardBody.appendChild(updateButton);
                cardBody.appendChild(deleteButton);
                card.appendChild(cardBody);
                resultContainer.appendChild(card);

                updateButton.addEventListener("click", (event) => {
                    event.preventDefault();
                    const id = item.id;
                    window.location.href = `../putcustomer.html?id=${id}`;
                });

      
                deleteButton.addEventListener("click", (event) => {
                    event.preventDefault(); 

                    

                    const id = item.id;

                    const apiUrlDELETE = `http://localhost:8080/customer/${id}`;
                    

                    //Petición para eliminar el clientes a a partir del ID
                    fetch(apiUrlDELETE, {
                        method: 'DELETE' 
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Error en la red');
                        }
                        
                        location.reload();

                    })
                    .catch(error => {
                        console.error('Hubo un problema con la eliminación:', error);
                        alert('No se pudo eliminar el customer: ' + error.message);
                    });
                });
            });
        })
        .catch(error => {
            console.error('Hubo un problema con la petición Fetch:', error);
            document.getElementById('result').textContent = 'Error: ' + error.message;
        });
});
