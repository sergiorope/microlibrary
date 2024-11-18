document.addEventListener("DOMContentLoaded", async () => {

const urlParams = new URLSearchParams(window.location.search);
const loanId = urlParams.get("id");
const apiUrlLoanbyIdGET = `http://localhost:8080/loan/${loanId}`;
const apiUrlCustomerGET = `http://localhost:8080/customer/list`;
const apiUrlProductGET = `http://localhost:8080/product/list`;



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

const apiUrlLoanLinesbyLoanGET = `http://localhost:8080/loanline/by-loan/${loanId}`;

const responseLoanLines = await fetch(apiUrlLoanLinesbyLoanGET);
if (!responseLoanLines.ok) {
  throw new Error("Error en la red");
}
const loanLines = await responseLoanLines.json();


const selectContainer =document.getElementById("selectsContainer");
selectContainer.className = "mt-2"


for(itemLoanLine of loanLines){


  const selectElement = document.createElement("select");

        const responseProductList = await fetch(apiUrlProductGET);
      if (!responseProductList.ok) {
        throw new Error("Error en la red");
      }
const ProductList = await responseProductList.json();

const apiUrlProductNameGET = `http://localhost:8080/loanline/product/${itemLoanLine.id}`;


const responseProductName= await fetch(apiUrlProductNameGET);
if (!responseProductName.ok) {
  throw new Error("Error en la red");
}
const ProductName = await responseProductName.text();



for(itemProduct of ProductList){


  const optionElement = document.createElement("option");



  optionElement.textContent=itemProduct.name;

  if(optionElement.textContent == ProductName){

    optionElement.selected = true;
  }

  selectElement.appendChild(optionElement);
  selectElement.className = "form-select mt-2 "


}

selectContainer.appendChild(selectElement);

  


}

const addButton = document.createElement("a");

  addButton.innerHTML = '<i class="bi bi-plus-circle"></i>';
  addButton.href = "#";
  addButton.className = "btn btn-primary mt-1";

  selectContainer.appendChild(addButton);







});

function getDateDifference(startDate, endDate) {
  const parseDate = (dateStr) => {
      const [day, month, year] = dateStr.split("-").map(Number);
      return new Date(year, month - 1, day);
  };

  const start = parseDate(startDate);
  const end = parseDate(endDate);

  const diffTime = Math.abs(end - start);

  const diffDays = diffTime / (1000 * 60 * 60 * 24);
  const diffWeeks = diffDays / 7;
  const diffMonths = (end.getFullYear() - start.getFullYear()) * 12 + (end.getMonth() - start.getMonth());
  const diffYears = diffMonths / 12;

  if (diffYears >= 1 && Number.isInteger(diffYears)) {
      return "year";
  } else if (diffMonths >= 1 && Number.isInteger(diffMonths)) {
      return "month";
  } else if (diffWeeks >= 1 && Number.isInteger(diffWeeks)) {
      return "week";
  } else {
      return "less than a week";
  }
}