document.addEventListener("DOMContentLoaded", async () => {
  console.time("TiempoTotal"); // Inicia el cronómetro
  const apiUrlGET = "http://localhost:8080/loan/list";
  const resultContainer = document.getElementById("result");

  try {
    const response = await fetch(apiUrlGET);
    if (!response.ok) {
      throw new Error("Error en la red");
    }
    const loans = await response.json();
    resultContainer.innerHTML = "";

    /*En esta página se utilizan las Promises, porque loan es el microservicio mas exigente, ya que utiliza "maestro de detalles",
    y maneja varios servicios a la vez entonces a diferencia de los demás, gracias a los promises reducimos el tiempo de respuesta gracias a que se realizan en paralelo las peticiones*/ 

    const loanPromises = loans.map(async (item) => {
      const validationDay = isFutureDate(item.end_date);

      if (!validationDay) {
        try {
          const apiUrlPUT = `http://localhost:8080/loan/${item.id}`;
          const bodyData = {
            start_date: item.start_date,
            status: "Expirado",
            end_date: item.end_date,
            customer_Id: item.customer_Id
          };

          console.log("Enviando solicitud PUT a:", apiUrlPUT);
          console.log("Datos enviados:", bodyData);

          const response = await fetch(apiUrlPUT, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bodyData),
          });

          if (!response.ok) {
            console.error(`Error en la red: ${response.status} - ${response.statusText}`);
            throw new Error(`Error en la red: ${response.status} - ${response.statusText}`);
          }

          const responseData = await response.json();
          console.log("Respuesta del servidor:", responseData);

        } catch (error) {
          console.error("Hubo un problema con la petición:", error);
        }
      }

      const [customerResponse, loanLinesResponse] = await Promise.all([
        fetch(`http://localhost:8080/loan/customer/${item.id}`).then((res) => res.text()),
        fetch(`http://localhost:8080/loanline/by-loan/${item.id}`).then((res) => res.json())
      ]);


      return { item, customerResponse, loanLinesResponse };
    });

    const loanData = await Promise.all(loanPromises);

    loanData.forEach(({ item, customerResponse, loanLinesResponse }) => {
      const accordionItem = document.createElement("div");
      accordionItem.className = "accordion-item mt-4";

      const accordionHeader = document.createElement("h2");
      accordionHeader.className = "accordion-header d-flex justify-content-between align-items-center";

      let classItem = item.status != "Pendiente" ? "statusExpired" : "statusPassed";

      const accordionButton = document.createElement("button");
      accordionButton.className = "accordion-button collapsed m-4";
      accordionButton.type = "button";
      accordionButton.setAttribute("data-bs-toggle", "collapse");
      accordionButton.setAttribute("data-bs-target", `#collapse-${item.id}`);
      accordionButton.setAttribute("aria-expanded", "false");
      accordionButton.setAttribute("aria-controls", `collapse-${item.id}`);
      accordionButton.innerHTML = `<b>Cliente:&nbsp; </b> ${customerResponse} &nbsp;&nbsp;| &nbsp;&nbsp; <b>Inicio:&nbsp; </b> ${item.start_date} &nbsp;&nbsp; | &nbsp;&nbsp; <b>Fin:&nbsp; </b> ${item.end_date}&nbsp;&nbsp; | &nbsp;&nbsp; <b>Estatus:&nbsp; </b> <span class="${classItem}">${item.status}</span>`;

      const buttonsContainer = document.createElement("div");
      buttonsContainer.className = "d-flex align-items-center me-2";

      const updateButton = document.createElement("a");
      updateButton.className = "btn btn-primary me-2";
      updateButton.textContent = "Actualizar";
      updateButton.href = `../putloan.html?id=${item.id}`;

      const deleteButton = document.createElement("a");
      deleteButton.className = "btn btn-danger";
      deleteButton.textContent = "Eliminar";

      buttonsContainer.appendChild(updateButton);
      buttonsContainer.appendChild(deleteButton);

      accordionHeader.appendChild(accordionButton);
      accordionHeader.appendChild(buttonsContainer);

      const accordionCollapse = document.createElement("div");
      accordionCollapse.id = `collapse-${item.id}`;
      accordionCollapse.className = "accordion-collapse collapse";
      accordionCollapse.setAttribute("data-bs-parent", "#accordionFlushExample");

      const accordionBody = document.createElement("div");
      accordionBody.className = "accordion-body";

      const loanLinesList = document.createElement("ul");
      loanLinesList.className = "loan-lines mt-2";

      // Manejar las líneas de préstamo
      loanLinesResponse.forEach((itemLoanline) => {
        const apiUrlGETProducts = `http://localhost:8080/loanline/product/${itemLoanline.id}`;
        fetch(apiUrlGETProducts)
          .then((res) => res.text())
          .then((products) => {
            const listItem = document.createElement("li");
            listItem.innerHTML = `<b>Producto</b>: ${products}`;
            loanLinesList.appendChild(listItem);
          })
          .catch((error) => console.error("Hubo un problema con la petición", error));
      });

      accordionBody.append(loanLinesList);
      accordionCollapse.appendChild(accordionBody);
      accordionItem.appendChild(accordionHeader);
      accordionItem.appendChild(accordionCollapse);
      resultContainer.appendChild(accordionItem);

      deleteButton.addEventListener("click", async (event) => {
        event.preventDefault();
        const apiUrlDELETE = `http://localhost:8080/loan/${item.id}`;

        try {
          const deleteResponse = await fetch(apiUrlDELETE, { method: "DELETE" });
          if (!deleteResponse.ok) {
            throw new Error("Error en la red");
          }

          for (const itemLoanline of loanLinesResponse) {
            const apiUrlLoanlineDELETE = `http://localhost:8080/loanline/${itemLoanline.id}`;
            const deleteResponseLoanLine = await fetch(apiUrlLoanlineDELETE, { method: "DELETE" });
            if (!deleteResponseLoanLine.ok) {
              throw new Error("Error en la red");
            }
          }

          location.reload();
        } catch (error) {
          console.error("Hubo un problema con la eliminación:", error);
          alert("No se pudo eliminar el préstamo o las líneas de préstamo: " + error.message);
        }
      });
    });
  } catch (error) {
    console.error("Hubo un problema con la petición Fetch:", error);
    resultContainer.textContent = "Error: " + error.message;
  }

  console.timeEnd("TiempoTotal"); 
});

function isFutureDate(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);
  const inputDate = new Date(year, month - 1, day);
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  return inputDate >= today;
}
