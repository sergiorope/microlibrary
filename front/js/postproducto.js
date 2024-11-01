const form = document.querySelector("#productinfo");

form.addEventListener("submit", (event) => {
    event.preventDefault(); 

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
            return response.json().then(err => { // Obtener el mensaje de error del servidor
                throw new Error(`Error ${response.status}: ${err.message}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log("Producto guardado exitosamente:", data);
    })
    .catch(error => {
        console.error("Hubo un problema con la petición Fetch:", error);
    });
});
