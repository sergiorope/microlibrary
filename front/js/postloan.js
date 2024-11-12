document.addEventListener("DOMContentLoaded", async () => {
const form = document.querySelector("#loaninfo");

const apiUrlPOST = "http://localhost:8080/loan/post";
const apiUrlLoanlinePOST = "http://localhost:8080/loanline/post";
const apiUrlGET = "http://localhost:8080/customer/list";
const apiUrlProductGET = "http://localhost:8080/product/list";

//Petición para mostrar todos los customers disponibles en el select
const response = await fetch(apiUrlGET);
if (!response.ok) {
  throw new Error("Error en la red");
}
const customers = await response.json();

const selectElement = document.getElementById("clientes");


for (const item of customers) {

    const optionElement = document.createElement("option");
    optionElement.textContent = item.name;
    optionElement.value = item.id;



selectElement.appendChild(optionElement);


}

const selectTime = document.getElementById("time");
const inputStart_Date = document.getElementById("start_date");
const inputEnd_Date = document.getElementById("end_date");
  



const saltolinea = document.createElement("br");
const addContainer = document.getElementById("addContainer");
const addContainerSelects = document.getElementById("addContainerSelects");
const addButton = document.createElement("a");
addButton.innerHTML = '<i class="bi bi-plus-circle"></i>';
addButton.href = "#";
addButton.className = "btn btn-primary mt-1"


addContainer.appendChild(addButton);
addContainer.appendChild(saltolinea);

let selectElementProduct;


// Click para aumentar todos los productos deseados en el préstamo
addButton.addEventListener("click", async (event) => {
  event.preventDefault();

  const response = await fetch(apiUrlProductGET);
  if (!response.ok) {
    throw new Error("Error en la red");
  }

  const products = await response.json();


  selectElementProduct = document.createElement("select");

  selectElementProduct.className = "form-select mt-2";


for (const item of products) {

    const optionElement = document.createElement("option");
    optionElement.textContent = item.name;
    optionElement.value = item.id;



    selectElementProduct.appendChild(optionElement);


    addContainerSelects.appendChild(selectElementProduct);

}

});

form.addEventListener("submit", async (event) => {
  event.preventDefault();



  let resultDate = "";
  for (const option of selectTime.options) {
    if(option.value == "year"){
      resultDate = addOneYear(inputStart_Date.value);
    } 
    
    if(option.value == "month"){
      resultDate = addOneMonth(inputStart_Date.value);
    } 
    
    if(option.value == "week"){
      resultDate = addOneWeek(inputStart_Date.value);
    }
  }


  //Para introducir el valor de la fecha final, cojemos el resultado de lo seleccionado en el select y lo pasamos al input
  inputEnd_Date.value = resultDate;


  emptyContainer.innerHTML = "";
  messageContainer.innerHTML = "";

  if (!inputStart_Date.value) {
    const customerEmpty = document.createElement("p");
    customerEmpty.textContent = "Por favor, rellene todos los campos";
    emptyContainer.appendChild(customerEmpty);
  } else {
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));

    console.log(formData.values)
    console.log(data.value)

   


      const selects = addContainerSelects.getElementsByTagName("select");

      try {
        const IdRepetitive = [];
    
        for (const select of selects) {
            const selectedOption = select.options[select.selectedIndex];
    
            if (IdRepetitive.includes(selectedOption.value)) {
                throw new Error("Error: no puedes poner dos productos iguales en el mismo préstamo");
            }
    
            IdRepetitive.push(selectedOption.value);
        }
    
        // Si llegamos aquí, no se lanzaron excepciones y podemos proceder con los POST
        const response = await fetch(apiUrlPOST, {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(data),
        });
    
        if (!response.ok) {
            const errorDetails = await response.json();
            throw new Error(`Error ${response.status}: ${errorDetails.message || "Unexpected error"}`);
        }
    
        const loanData = await response.json();
        const loanId = parseInt(loanData.id);
    
        // Aquí procesamos las líneas de préstamo
        for (const selectedProductId of IdRepetitive) {
            const loanLineData = { loan_Id: loanId, product_Id: selectedProductId };
    
            const responseLoanLine = await fetch(apiUrlLoanlinePOST, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                body: JSON.stringify(loanLineData),
            });
    
            if (!responseLoanLine.ok) {
                const errorDetails = await responseLoanLine.json();
                throw new Error(`Error en Loanline ${responseLoanLine.status}: ${errorDetails.message || "Unexpected error"}`);
            }
    
            const responseData = await responseLoanLine.json();
            console.log("Línea de préstamo creada:", responseData);
        }


        const customerNew = document.createElement("p");
        customerNew.textContent = "Añadido correctamente";
        messageContainer.appendChild(customerNew);
    
       
    
    } catch (error) {
        console.error(error.message);

        const productError = document.createElement("p");

        productError.className="errormsgproduct";

        productError.textContent = "Error no puede haber dos productos iguales en un préstamo";
        messageContainer.appendChild(productError);
    }
    

          

      


    

    form.reset();
  }


});


//Funciones para calcular de manera automática el tiempo que durará el prestamo
function addOneYear(dateString) {
  
  const [day, month, year] = dateString.split("-").map(Number);
  
  const date = new Date(year, month - 1, day);
  
  date.setFullYear(date.getFullYear() + 1);
  
  const newDay = String(date.getDate()).padStart(2, '0');
  const newMonth = String(date.getMonth() + 1).padStart(2, '0');
  const newYear = date.getFullYear();
  
  return `${newDay}-${newMonth}-${newYear}`;
}

function addOneMonth(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);
  
  const date = new Date(year, month - 1, day);
  
  date.setMonth(date.getMonth() + 1);
  
  const newDay = String(date.getDate()).padStart(2, '0');
  const newMonth = String(date.getMonth() + 1).padStart(2, '0');
  const newYear = date.getFullYear();
  
  return `${newDay}-${newMonth}-${newYear}`;
}

function addOneWeek(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);
  
  const date = new Date(year, month - 1, day);
  
  date.setDate(date.getDate() + 7);
  
  const newDay = String(date.getDate()).padStart(2, '0');
  const newMonth = String(date.getMonth() + 1).padStart(2, '0');
  const newYear = date.getFullYear();
  
  return `${newDay}-${newMonth}-${newYear}`;
}

});
