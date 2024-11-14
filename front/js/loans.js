document.addEventListener("DOMContentLoaded", async () => {
    const apiUrlGET = "http://localhost:8080/loan/list";
    


    const resultContainer = document.getElementById("result");
    
    const accordionAccordion= document.getElementById("accordionFlushExample");

    
  
    try {
      // Recuperar todos los loan
      const response = await fetch(apiUrlGET);
      if (!response.ok) {
        throw new Error("Error en la red");
      }
      const loans = await response.json();
  
      resultContainer.innerHTML = "";
  
  
      for (const item of loans) {

        

        const id = item.id;

        const customerResponse = await fetch(`http://localhost:8080/loan/customer/${id}`);

        const dataCustomer = await customerResponse.text();


        const apiUrlLoanLinebyLoanGET = `http://localhost:8080/loanline/by-loan/${id}`;

         try {
            // Recuperar todos los loan
            const responseLoanLinesGET = await fetch(apiUrlLoanLinebyLoanGET);
            if (!response.ok) {
              throw new Error("Error en la red");
            }
            const loanlinesCollapse = await responseLoanLinesGET.json();

          

            for(const itemLoanline of loanlinesCollapse){


              const containerElement = document.createElement("div");

              containerElement.textContent  = itemLoanline.id;

              resultContainer.appendChild(containerElement);

              


              /*

              const accordionitem = document.createElement("div");
              accordionitem.classList.add("accordion-item");

              const accordionHeader = document.createElement("h2");
              accordionHeader.classList.add("accordion-header");

              const accordionButtonCollapsed = document.createElement("button");
              accordionButtonCollapsed.classList.add("accordion-button", "collapsed");
              accordionButtonCollapsed.type = "button";
              accordionButtonCollapsed.dataset.bsToggle = "collapse";
              accordionButtonCollapsed.dataset.bsTarget = "#flush-collapseOne";
              accordionButtonCollapsed.ariaExpanded = "false";
              accordionButtonCollapsed.ariaControls = "flush-collapseOne";

              accordionButtonCollapsed.textContent = itemLoanline.id;

              accordionAccordion.appendChild(accordionitem,accordionHeader,accordionButtonCollapsed);

              */





            

            }

          }  catch (error) {
            console.error("Hubo un problema con la petición Fetch:", error);
            resultContainer.textContent = "Error: " + error.message;
          }

           

        const card = document.createElement("div");
        card.className = "card mb-2";
  
        const updateButton = document.createElement("a");
        updateButton.className = "btn btn-primary";
        updateButton.textContent = "Editar";
        updateButton.href = `../putloan.html?id=${id}`; 


        const deleteButton = document.createElement("a");
        deleteButton.className = "btn btn-danger boton-eliminar";
        deleteButton.textContent = "Eliminar";
  
        const cardBody = document.createElement("div");
        cardBody.className = "card-body d-flex justify-content-between align-items-center";
  
        const startDateElement = document.createElement("p");
        startDateElement.className = "loan-start_date mt-2";
        startDateElement.innerHTML = `<b>Dia finalización:</b> ${item.end_date}`;
  
        const endDateElement = document.createElement("p");
        endDateElement.className = "loan-end_date mt-2";
        endDateElement.innerHTML = `<b>Día inicio:</b> ${item.start_date}`;

        const statusElement = document.createElement("p");
        statusElement.className = "loan-status mt-2";
        statusElement.innerHTML = `<b>Estatus:</b> ${item.status}`;

        const customerElement = document.createElement("p");
        customerElement.className = "loan-customer mt-2";
        customerElement.innerHTML = `<b>Cliente:</b> ${dataCustomer}`;

  
        cardBody.append(customerElement, startDateElement, endDateElement, statusElement, updateButton, deleteButton);
        card.appendChild(cardBody);
        resultContainer.appendChild(card); 
       
  
        
        deleteButton.addEventListener("click", async (event) => {
          event.preventDefault();
          const apiUrlDELETE = `http://localhost:8080/loan/${id}`;
  
          try {
            const deleteResponse = await fetch(apiUrlDELETE, { method: "DELETE" });
            if (!deleteResponse.ok) {
              throw new Error("Error en la red");
            }

           
          } catch (error) {
            console.error("Hubo un problema con la eliminación:", error);
            alert("No se pudo eliminar el prestamo: " + error.message);
          }

       


         
          


          try {
            // Recuperar todos los loan
            const responseLoanLines = await fetch(apiUrlLoanLinebyLoanGET);
            if (!response.ok) {
              throw new Error("Error en la red");
            }
            const loanlines = await responseLoanLines.json();

            for(const itemLoanline of loanlines){

              const apiUrlLoanlineDELETE = `http://localhost:8080/loanline/${itemLoanline.id}`;

              


          try {
            const deleteResponseLoanLine = await fetch(apiUrlLoanlineDELETE, { method: "DELETE" });
            if (!deleteResponseLoanLine.ok) {
              throw new Error("Error en la red");
            }
            
          } catch (error) {
            console.error("Hubo un problema con la eliminación:", error);
            alert("No se pudo eliminar los loanlines: " + error.message);
          }

            }

          } catch (error) {
            console.error("Hubo un problema con la petición Fetch:", error);
            resultContainer.textContent = "Error: " + error.message;
          }


          location.reload(); 

        });
      }
    } catch (error) {
      console.error("Hubo un problema con la petición Fetch:", error);
      resultContainer.textContent = "Error: " + error.message;
    }
  });
  