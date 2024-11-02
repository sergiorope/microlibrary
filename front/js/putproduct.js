document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    const form = document.querySelector("#product-update");
    const apiUrlID = `http://localhost:8080/product/${productId}`;


    //Petición para recuperar el producto especifico a partir del ID proporcionado en la URL de la página anterior
    fetch(apiUrlID)
    .then(response => response.json())
    .then(data => {
        document.getElementById('product-name-update').value = data.name;
        document.getElementById('product-description-update').value = data.description;
        document.getElementById('product-autor-update').value = data.autor;
        document.getElementById('product-image-update').value = data.image;
    })
    .catch(error => {
        console.error('Error al cargar el producto:', error);
        alert('No se pudo cargar el producto');
        window.location.href = "../products.html";
    });

  


    form.addEventListener("submit", (event) => {
        event.preventDefault(); 

    const name = document.getElementById('product-name-update').value.trim();
    const description = document.getElementById('product-description-update').value.trim();
    const autor = document.getElementById('product-autor-update').value.trim();
    const imagen = document.getElementById('product-image-update').value.trim(); 


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

    //Petición para actualizar el producto una vez se pulse el submit y se hayan validado los datos
    fetch(apiUrlID,{
    method: "PUT",
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

    const productoUpdate = document.createElement('p');
    productoUpdate.className = 'productoUpdate';
    productoUpdate.textContent = "Producto Actualizado";
    messageContainer.appendChild(productoUpdate);

    form.reset();
    return response.json(); 
})
.then(data => {
    console.log("Producto editado exitosamente:", data);
})
.catch(error => {
    console.error("Hubo un problema con la petición Fetch:", error);
});


}})

});

