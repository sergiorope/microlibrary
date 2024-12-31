document.addEventListener("DOMContentLoaded", async () => {
  const apiUrlGET = "http://localhost:8080/customer/list";
  const resultContainer = document.getElementById("result");

  try {
    const response = await fetch(apiUrlGET);

    if (response.status === 404) {
      const noList = document.createElement("div");
      noList.className = "noList";
      noList.textContent = "No hay clientes";

      resultContainer.innerHTML = ""; 
      resultContainer.appendChild(noList); 

      return; 
    }

    if (!response.ok) {
      throw new Error("Error en la red");
    }

    const customers = await response.json();

    resultContainer.innerHTML = "";

    for (const item of customers) {
      const id = item.id;

      const partnerResponse = await fetch(`http://localhost:8080/customer/partner/${id}`);
      if (!partnerResponse.ok) {
        throw new Error("Error en la red");
      }
      const dataPartner = await partnerResponse.text();

      const card = document.createElement("div");
      card.className = "card mb-2";

      const updateButton = document.createElement("a");
      updateButton.className = "btn btn-primary";
      updateButton.textContent = "Editar";
      updateButton.href = `../putcustomer.html?id=${id}`;
      
      const deleteButton = document.createElement("a");
      deleteButton.className = "btn btn-danger boton-eliminar";
      deleteButton.textContent = "Eliminar";

      const cardBody = document.createElement("div");
      cardBody.className = "card-body d-flex justify-content-between align-items-center";

      const nameElement = document.createElement("p");
      nameElement.className = "customer-name mt-2";
      nameElement.innerHTML = `<b>Nombre:</b> ${item.name}`;

      const surnameElement = document.createElement("p");
      surnameElement.className = "customer-surname mt-2";
      surnameElement.innerHTML = `<b>Apellidos:</b> ${item.surname}`;

      const partnerElement = document.createElement("p");
      partnerElement.className = "customer-partner mt-2";
      partnerElement.innerHTML = `<b>Grupo:</b> ${dataPartner}`;

      // Crear un contenedor adicional para separar los datos con el | entre ellos
      const separator = document.createElement("span");
      separator.innerHTML = `&nbsp;&nbsp;|&nbsp;&nbsp;`;

      cardBody.append(nameElement, separator.cloneNode(true), surnameElement, separator.cloneNode(true), partnerElement, updateButton, deleteButton);
      card.appendChild(cardBody);
      resultContainer.appendChild(card);

      deleteButton.addEventListener("click", async (event) => {
        event.preventDefault();
        const apiUrlDELETE = `http://localhost:8080/customer/${id}`;

        try {
          const deleteResponse = await fetch(apiUrlDELETE, { method: "DELETE" });
          if (!deleteResponse.ok) {
            throw new Error("Error en la red");
          }
          location.reload();
        } catch (error) {
          console.error("Hubo un problema con la eliminación:", error);
          alert("No se pudo eliminar el customer: " + error.message);
        }
      });
    }
  } catch (error) {
    console.error("Hubo un problema con la petición Fetch:", error);
    resultContainer.textContent = "Error: " + error.message;
  }
});
