document.addEventListener("DOMContentLoaded", async () => {
  const apiUrlGET = "http://localhost:8080/loan/list";
  const resultContainer = document.getElementById("result");

  try {
    // Recuperar todos los préstamos
    const response = await fetch(apiUrlGET);
    if (!response.ok) {
      throw new Error("Error en la red");
    }
    const loans = await response.json();
    resultContainer.innerHTML = "";

    for (const item of loans) {

      const validationDay =isFutureDate(item.end_date);


      if(!validationDay){

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


      const id = item.id;

      // Obtener información del cliente del préstamo
      const customerResponse = await fetch(`http://localhost:8080/loan/customer/${id}`);
      const dataCustomer = await customerResponse.text();

      // Obtener las líneas de préstamo asociadas
      const apiUrlLoanLinebyLoanGET = `http://localhost:8080/loanline/by-loan/${id}`;
      const responseLoanLinesGET = await fetch(apiUrlLoanLinebyLoanGET);
      if (!responseLoanLinesGET.ok) {
        throw new Error("Error en la red");
      }
      const loanlines = await responseLoanLinesGET.json();

      const accordionItem = document.createElement("div");
      accordionItem.className = "accordion-item mt-4";

      const accordionHeader = document.createElement("h2");
      accordionHeader.className = "accordion-header d-flex justify-content-between align-items-center";

      let classItem = "";


      if(item.status != "Pendiente"){

          classItem = "statusExpired";
      }else {

        classItem = "statusPassed";
      }

      const accordionButton = document.createElement("button");
      accordionButton.className = "accordion-button collapsed m-4";
      accordionButton.type = "button";
      accordionButton.setAttribute("data-bs-toggle", "collapse");
      accordionButton.setAttribute("data-bs-target", `#collapse-${id}`);
      accordionButton.setAttribute("aria-expanded", "false");
      accordionButton.setAttribute("aria-controls", `collapse-${id}`);
      accordionButton.innerHTML = `<b>Cliente:&nbsp; </b> ${dataCustomer} &nbsp;&nbsp;| &nbsp;&nbsp; <b>Inicio:&nbsp; </b> ${item.start_date} &nbsp;&nbsp; | &nbsp;&nbsp; <b>Fin:&nbsp; </b> ${item.end_date}&nbsp;&nbsp; | &nbsp;&nbsp; <b>Estatus:&nbsp; </b> <span class="${classItem}">${item.status}</span>`;


      const buttonsContainer = document.createElement("div");
      buttonsContainer.className = "d-flex align-items-center me-2";

      

   
      const updateButton = document.createElement("a");
      updateButton.className = "btn btn-primary me-2";
      updateButton.textContent = "Actualizar";
      updateButton.href = `../putloan.html?id=${id}`;

      const deleteButton = document.createElement("a");
      deleteButton.className = "btn btn-danger";
      deleteButton.textContent = "Eliminar";

    
      buttonsContainer.appendChild(updateButton);
      buttonsContainer.appendChild(deleteButton);

      accordionHeader.appendChild(accordionButton);
      accordionHeader.appendChild(buttonsContainer);

      const accordionCollapse = document.createElement("div");
      accordionCollapse.id = `collapse-${id}`;
      accordionCollapse.className = "accordion-collapse collapse";
      accordionCollapse.setAttribute("data-bs-parent", "#accordionFlushExample");

      const accordionBody = document.createElement("div");
      accordionBody.className = "accordion-body";

      const loanLinesList = document.createElement("ul");
      loanLinesList.className = "loan-lines mt-2";
      for (const itemLoanline of loanlines) {
        try {
            const apiUrlGETProducts = `http://localhost:8080/loanline/product/${itemLoanline.id}`;
            const response = await fetch(apiUrlGETProducts);
            if (!response.ok) {
                throw new Error("Error en la red");
            }
    
            // Procesa los datos recibidos de la API si es necesario
            const products = await response.text();

          
              const listItem = document.createElement("li");
              listItem.innerHTML = `<b>Producto</b>: ${products}`;
              loanLinesList.appendChild(listItem);
            
    
        } catch (error) {
            console.error("Hubo un problema con la petición", error);
        }
     
        
    }
    

      accordionBody.append(loanLinesList);

      accordionCollapse.appendChild(accordionBody);

      accordionItem.appendChild(accordionHeader);
      accordionItem.appendChild(accordionCollapse);

      resultContainer.appendChild(accordionItem);

      // Manejador para eliminar préstamo y loanlines
      deleteButton.addEventListener("click", async (event) => {
        event.preventDefault();
        const apiUrlDELETE = `http://localhost:8080/loan/${id}`;

        try {
          const deleteResponse = await fetch(apiUrlDELETE, { method: "DELETE" });
          if (!deleteResponse.ok) {
            throw new Error("Error en la red");
          }

          // Eliminar las loanlines asociadas
          for (const itemLoanline of loanlines) {
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
    }
  } catch (error) {
    console.error("Hubo un problema con la petición Fetch:", error);
    resultContainer.textContent = "Error: " + error.message;
  }
});

function isFutureDate(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);
  
  const inputDate = new Date(year, month - 1, day); 


  const today = new Date();
  today.setHours(0, 0, 0, 0); 

 
  return inputDate >= today;
}
