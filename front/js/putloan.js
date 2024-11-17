document.addEventListener("DOMContentLoaded", async () => {

const urlParams = new URLSearchParams(window.location.search);
const loanId = urlParams.get("id");
const apiUrlLoanbyIdGET = `http://localhost:8080/loan/${loanId}`;
const apiUrlCustomerGET = `http://localhost:8080/customer/list`;


const response = await fetch(apiUrlLoanbyIdGET);
if (!response.ok) {
  throw new Error("Error en la red");
}
const loan = await response.json();

const inputStart_Date = document.getElementById("loan-startdate-update");

inputStart_Date.value = loan.start_date;



const customerSelect = document.getElementById("customers");


const responseCustomer = await fetch(apiUrlCustomerGET);
if (!responseCustomer.ok) {
  throw new Error("Error en la red");
}
const customers = await responseCustomer.json();


const apiUrlCustomerNameGET = `http://localhost:8080/loan/customer/${loan.id}`;

const responseCustomerName = await fetch(apiUrlCustomerNameGET);
if (!responseCustomerName.ok) {
  throw new Error("Error en la red");
}
const customerName = await responseCustomerName.text();




for(item of customers){


    const optionElement = document.createElement("option");

    optionElement.textContent = item.name;

    

    if(optionElement.textContent == customerName){

        optionElement.selected = true;
    }

    customerSelect.appendChild(optionElement);

    
}


});