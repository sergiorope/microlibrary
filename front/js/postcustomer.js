const form = document.querySelector("#customerinfo");

const apiUrlPOST = "http://localhost:8080/customer/post";
const apiUrlGET = "http://localhost:8080/partner/list";

//Petición para mostrar todos los groups partners disponibles en el select
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

      selectElement.appendChild(optionElement);
    });
  });

form.addEventListener("submit", (event) => {
  event.preventDefault();

  const name = document.getElementById("name").value.trim();
  const surname = document.getElementById("description").value.trim();

  const emptyContainer = document.getElementById("emptyContainer");
  const messageContainer = document.getElementById("messageContainer");

  emptyContainer.innerHTML = "";
  messageContainer.innerHTML = "";

  if (!name || !surname) {
    const customerEmpty = document.createElement("p");
    customerEmpty.textContent = "Por favor, rellene todos los campos";
    emptyContainer.appendChild(customerEmpty);
  } else {
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));

    //Petición para insertar los datos del customer despues de pulsar submit y que haya sido validado
    fetch(apiUrlPOST, {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (!response.ok) {
          return response.json().then((err) => {
            throw new Error(`Error ${response.status}: ${err.message}`);
          });
        }

        const customerNew = document.createElement("p");
        customerNew.textContent = "Añadido correctamente";
        messageContainer.appendChild(customerNew);

        form.reset();
        return response.json();
      })
      .then((data) => {
        console.log("Customer guardado exitosamente:", data);
      })
      .catch((error) => {
        console.error("Hubo un problema con la petición Fetch:", error);
      });
  }
});
