document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const customerId = urlParams.get("id");
  const form = document.querySelector("#customer-update");
  const apiUrlID = `http://localhost:8080/customer/${customerId}`;
  const apiUrlGET = "http://localhost:8080/partner/list";

  fetch(apiUrlID)
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("customer-name-update").value = data.name;
      document.getElementById("customer-surname-update").value = data.surname;
    })
    .catch((error) => {
      console.error("Error al cargar el producto:", error);
      alert("No se pudo cargar el producto");
      window.location.href = "../customers.html";
    });

  fetch(`http://localhost:8080/customer/partner/${customerId}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error en la red");
      }

      return response.text();
    })
    .then((dataPartner) => {
      console.log(dataPartner);

      fetch(apiUrlGET)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error en la red");
          }
          return response.json();
        })
        .then((data) => {
          console.log(data);

          const selectElement = document.getElementById("clientes");

          data.forEach((item) => {
            const optionElement = document.createElement("option");
            optionElement.textContent = item.name;
            optionElement.value = item.id;

            if (item.name == dataPartner) {
              optionElement.selected = true;
            }

            selectElement.appendChild(optionElement);
          });
        });
    });



    form.addEventListener("submit", (event) => {
        event.preventDefault(); 

    const name = document.getElementById('customer-name-update').value.trim();
    const surname = document.getElementById('customer-surname-update').value.trim();
    const partner = document.getElementById('clientes').value



    const emptyContainer = document.getElementById("emptyContainer");
    const messageContainer = document.getElementById("messageContainer");

    emptyContainer.innerHTML = ""; 
    messageContainer.innerHTML = ""; 

    if (!name || !surname) {
        const customerEmpty = document.createElement('p');
        customerEmpty.className = 'customerEmpty';
        customerEmpty.textContent = "Por favor, rellene todos los campos";
        emptyContainer.appendChild(customerEmpty);
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

    const customerUpdate = document.createElement('p');
    customerUpdate.className = 'customerUpdate';
    customerUpdate.textContent = "Elemento Actualizado";
    messageContainer.appendChild(customerUpdate);

    form.reset();
    return response.json(); 
})
.then(data => {
    console.log("Customer editado exitosamente:", data);
})
.catch(error => {
    console.error("Hubo un problema con la petición Fetch:", error);
});


}})






});
