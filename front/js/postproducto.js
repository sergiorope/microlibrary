const form = document.querySelector("#productinfo");

form.addEventListener("submit", (event) => {
    event.preventDefault(); 

    const name = document.getElementById('name').value.trim();
    const description = document.getElementById('description').value.trim();
    const autor = document.getElementById('autor').value.trim();
    const imagen = document.getElementById('image').value.trim(); 

    const emptyContainer = document.getElementById("emptyContainer");
    const messageContainer = document.getElementById("messageContainer");


    emptyContainer.innerHTML = ""; 
    messageContainer.innerHTML = ""; 

    if (!name || !description || !autor || !imagen) {
        const productEmpty = document.createElement('p');
        productEmpty.className = 'productoEmpty';
        productEmpty.textContent = "Por favor, rellene todos los campos";
        emptyContainer.appendChild(productEmpty);
    } else {
        const formData = new FormData(form);
        const data = {};
        formData.forEach((value, key) => (data[key] = value));

        fetch("http://localhost:8080/product/post", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { 
                    throw new Error(`Error ${response.status}: ${err.message}`);
                });
            }

            const productoNew = document.createElement('p');
            productoNew.className = 'productoNew';
            productoNew.textContent = "Producto Añadido";
            messageContainer.appendChild(productoNew);

            form.reset();
            return response.json(); 
        })
        .then(data => {
            console.log("Producto guardado exitosamente:", data);
        })
        .catch(error => {
            console.error("Hubo un problema con la petición Fetch:", error);
        });
    }
});
