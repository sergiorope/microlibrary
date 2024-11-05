document.addEventListener("DOMContentLoaded", () => {
  const apiUrlGET = "http://localhost:8080/product/list";

  //Petición para recuperar todos los productos al cargar la página
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
        const card = document.createElement("div");
        card.className = "card mb-3 card-size";

        const updateButton = document.createElement("a");
        updateButton.className = "btn btn-primary";
        updateButton.textContent = "Editar";
        updateButton.href = "../putproducto.html";

        const deleteButton = document.createElement("a");
        deleteButton.className = "btn btn-danger ms-2 boton-eliminar";
        deleteButton.textContent = "Eliminar";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body-product";

        const buttonsBody = document.createElement("div");
        cardBody.className = "body-button";

        const nameElement = document.createElement("h5");
        nameElement.className = "product-name";
        nameElement.textContent = item.name;

        const autorElement = document.createElement("P");
        autorElement.className = "product-autor";
        autorElement.textContent = item.autor;

        const descriptionElement = document.createElement("p");
        descriptionElement.className = "product-description";
        descriptionElement.textContent = item.description;

        const imageElement = document.createElement("img");
        imageElement.className = "product-image";
        imageElement.src = item.image;
        imageElement.alt = item.name;

        cardBody.appendChild(imageElement);
        cardBody.appendChild(autorElement);
        cardBody.appendChild(nameElement);
        cardBody.appendChild(descriptionElement);
        buttonsBody.appendChild(updateButton);
        buttonsBody.appendChild(deleteButton);
        cardBody.appendChild(buttonsBody);

     
        card.appendChild(cardBody);
        resultContainer.appendChild(card);

        updateButton.addEventListener("click", (event) => {
          event.preventDefault();
          const id = item.id;
          window.location.href = `../putproduct.html?id=${id}`;
        });

        deleteButton.addEventListener("click", (event) => {
          event.preventDefault();

          const id = item.id;

          const apiUrlDELETE = `http://localhost:8080/product/${id}`;

          //Petición para eliminar el producto a a partir del ID
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
              alert("No se pudo eliminar el producto: " + error.message);
            });
        });
      });
    })
    .catch((error) => {
      console.error("Hubo un problema con la petición Fetch:", error);
      document.getElementById("result").textContent = "Error: " + error.message;
    });
});
