document.addEventListener("DOMContentLoaded", () => {
  const apiUrlGET = "http://localhost:8080/customer/list";

  //Petición para recuperar todos los clientes al cargar la página
  fetch(apiUrlGET)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error en la red");
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);

      const resultContainer = document.getElementById("result");
      resultContainer.innerHTML = "";

      data.forEach((item) => {
        const id = item.id;

        fetch(`http://localhost:8080/customer/partner/${id}`)
          .then((response) => {
            if (!response.ok) {
              throw new Error("Error en la red");
            }

            return response.text();
          })
          .then((dataPartner) => {
            console.log(dataPartner);


            const card = document.createElement("div");
            card.className = "card mb-2"; 

            const updateButton = document.createElement("a");
            updateButton.className = "btn btn-primary";
            updateButton.textContent = "Editar";
            updateButton.href = "../putcustomer.html";



            const deleteButton = document.createElement("a");
            deleteButton.className = "btn btn-danger boton-eliminar";
            deleteButton.textContent = "Eliminar";

            const cardBody = document.createElement("div");
            cardBody.className = "card-body d-flex justify-content-between align-items-center";

            const nameElement = document.createElement("p");
            nameElement.className = "customer-name mt-2";
            nameElement.innerHTML = "<b>Nombre:</b> " + item.name;

            const surnameElement = document.createElement("p");
            surnameElement.className = "customer-surname mt-2";
            surnameElement.innerHTML = "<b>Apellidos:</b> " + item.surname;

            const partnerElement = document.createElement("p");
            partnerElement.className = "customer-partner mt-2";
            partnerElement.innerHTML = "<b>Grupo:</b> " + dataPartner;

            cardBody.appendChild(nameElement);
            cardBody.appendChild(surnameElement);
            cardBody.appendChild(partnerElement);
            cardBody.appendChild(updateButton);
            cardBody.appendChild(deleteButton);
            card.appendChild(cardBody);
            resultContainer.appendChild(card); // Añadir la tarjeta al contenedor

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
                method: "DELETE",
              })
                .then((response) => {
                  if (!response.ok) {
                    throw new Error("Error en la red");
                  }

                  location.reload();
                })
                .catch((error) => {
                  console.error("Hubo un problema con la eliminación:", error);
                  alert("No se pudo eliminar el customer: " + error.message);
                });
            });
          });
      });
    })
    .catch((error) => {
      console.error("Hubo un problema con la petición Fetch:", error);
      document.getElementById("result").textContent = "Error: " + error.message;
    });
});
