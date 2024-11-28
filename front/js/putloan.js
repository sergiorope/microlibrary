document.addEventListener("DOMContentLoaded", async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const loanId = urlParams.get("id");
  const apiUrlLoanbyIdGET = `http://localhost:8080/loan/${loanId}`;
  const apiUrlCustomerGET = `http://localhost:8080/customer/list`;
  const apiUrlProductGET = `http://localhost:8080/product/list`;

  const idsRemoveLoanLines = new Set();

  emptyContainer.innerHTML = "";
  messageContainer.innerHTML = "";

  const form = document.querySelector("#loan-update");

  const selectTime = document.getElementById("time");

  const selectContainer = document.getElementById("selectsContainer");

  selectContainer.className = "mt-2";

  const response = await fetch(apiUrlLoanbyIdGET);
  if (!response.ok) {
    throw new Error("Error en la red");
  }
  const loan = await response.json();

  let element = getDateDifference(loan.start_date, loan.end_date);

  for (optionElement of selectTime.options) {
    if (optionElement.value == element) {
      optionElement.selected = true;
    }
  }

  const addButton = document.createElement("a");

  addButton.innerHTML = '<i class="bi bi-plus-circle"></i>';
  addButton.href = "#";
  addButton.className = "btn btn-primary mt-1";

  selectContainer.appendChild(addButton);

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

  for (item of customers) {
    const optionElement = document.createElement("option");

    optionElement.textContent = item.name;
    optionElement.value = item.id;

    if (optionElement.textContent == customerName) {
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

  for (itemLoanLine of loanLines) {
    const selectElement = document.createElement("select");

    const responseProductList = await fetch(apiUrlProductGET);
    if (!responseProductList.ok) {
      throw new Error("Error en la red");
    }
    const ProductList = await responseProductList.json();

    const apiUrlProductNameGET = `http://localhost:8080/loanline/product/${itemLoanLine.id}`;

    const responseProductName = await fetch(apiUrlProductNameGET);
    if (!responseProductName.ok) {
      throw new Error("Error en la red");
    }
    const ProductName = await responseProductName.text();

    for (itemProduct of ProductList) {
      const optionElement = document.createElement("option");

      optionElement.textContent = itemProduct.name;
      optionElement.value = itemProduct.id;

      if (optionElement.textContent == ProductName) {
        optionElement.selected = true;
      }

      selectElement.appendChild(optionElement);
      selectElement.className = "form-select mt-2 ";
    }

    const selectIndividualContainer = document.createElement("div");

    selectIndividualContainer.className = "individual-select d-flex align-items-center mt-2 w-100";

    const deleteButton = document.createElement("a");
    deleteButton.className = "btn btn-danger ms-2";
    deleteButton.textContent = "-";

    selectElement.id = itemLoanLine.id;

    selectIndividualContainer.appendChild(selectElement);
    selectIndividualContainer.appendChild(deleteButton);
    selectContainer.appendChild(selectIndividualContainer);

    deleteButton.addEventListener("click", () => {
      const selectEliminado = selectIndividualContainer.querySelector("select");

      idsRemoveLoanLines.add(selectEliminado.id);

      selectIndividualContainer.remove();
    });
  }

  addButton.addEventListener("click", async (event) => {
    const selectElement = document.createElement("select");

    const responseProductList = await fetch(apiUrlProductGET);
    if (!responseProductList.ok) {
      throw new Error("Error en la red");
    }
    const ProductList = await responseProductList.json();

    for (productItem of ProductList) {
      const optionElement = document.createElement("option");

      optionElement.textContent = productItem.name;
      optionElement.value = productItem.id;

      selectElement.appendChild(optionElement);
    }

    selectElement.className = "form-select mt-2";

    const selectIndividualContainer = document.createElement("div");
    selectIndividualContainer.className = "d-flex align-items-center mt-1";

    const deleteButton = document.createElement("a");
    deleteButton.className = "btn btn-danger ms-2";
    deleteButton.textContent = "-";

    selectIndividualContainer.appendChild(selectElement);
    selectIndividualContainer.appendChild(deleteButton);
    selectContainer.appendChild(selectIndividualContainer);

    deleteButton.addEventListener("click", () => {
      selectIndividualContainer.remove();
    });
  });

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(form);

    const startDate = document.getElementById("loan-startdate-update").value;
    const endDate = document.getElementById("time").value;
    const clientes = document.getElementById("customers").value;
    const status = document.getElementById("status").value;

    let endDateData;

    if (startDate.trim() != "") {
      if (endDate === "year") {
        endDateData = addOneYear(startDate);
      } else if (endDate === "month") {
        endDateData = addOneMonth(startDate);
      } else if (endDate === "week") {
        endDateData = addOneWeek(startDate);
      } else {
        console.error("El valor de 'endDate' no es válido.");
        alert("Error: Selecciona un período válido.");
        return;
      }

      console.log("Datos recopilados:", {
        startDate,
        endDateData,
        status,
        clientes,
      });

      formData.set("start_date", startDate);
      formData.set("end_date", endDateData);
      formData.set("status", status);
      formData.set("customer_Id", clientes);

      const data = {};
      formData.forEach((value, key) => (data[key] = value));

      console.log(data);

      const apiUrlPUT = `http://localhost:8080/loan/${loanId}`;

      try {
        const responseUPDATE = await fetch(apiUrlPUT, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });

        // Manejar la respuesta
        if (responseUPDATE.ok) {
          console.log("Solicitud PUT exitosa:", await responseUPDATE.json());
        } else {
          console.error(
            `Error en la solicitud: ${responseUPDATE.status} - ${responseUPDATE.statusText}`
          );
          alert(`Error al actualizar: ${responseUPDATE.status}`);
        }
      } catch (error) {
        console.error("Error al realizar la solicitud:", error);
        alert(
          "Error: No se pudo actualizar. Verifica tu conexión o inténtalo más tarde."
        );
      }

      const selects = selectContainer.querySelectorAll("select");

      for (const selectElement of selects) {
        console.log(selectElement.id + "wdwdwd");

        const apiUrlLoanLinePUT = `http://localhost:8080/loanline/${selectElement.id}`;

        const selectedOption =
          selectElement.options[selectElement.selectedIndex];
        const selectedOptionValue = selectedOption.value;

        const loanLineData = {
          loan_Id: loanId,
          product_Id: selectedOptionValue,
        };

        try {
          const responseUPDATE = await fetch(apiUrlLoanLinePUT, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loanLineData),
          });

          // Manejar la respuesta
          if (responseUPDATE.ok) {
            console.log("Solicitud PUT exitosa:", await responseUPDATE.json());
          } else {
            console.error(
              `Error en la solicitud: ${responseUPDATE.status} - ${responseUPDATE.statusText}`
            );
          }
        } catch (error) {
          console.error("Error al realizar la solicitud:", error);
        }

        if (selectElement.id.trim() === "") {
          const apiUrlLoanLinePOST = `http://localhost:8080/loanline/post`;

          try {
            const responsePOST = await fetch(apiUrlLoanLinePOST, {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify(loanLineData),
            });

            // Manejar la respuesta
            if (responseUPDATE.ok) {
              console.log("Solicitud POST exitosa:", await responsePOST.json());
            } else {
              console.error(
                `Error en la solicitud: ${responsePOST.status} - ${responsePOST.statusText}`
              );
            }
          } catch (error) {
            console.error("Error al realizar la solicitud:", error);
          }
        }
      }

      for (itemDeleteIdRemove of idsRemoveLoanLines) {
        const apiUrlLoanLinesDELETE = `http://localhost:8080/loanline/${itemDeleteIdRemove}`;

        try {
          const deleteResponse = await fetch(apiUrlLoanLinesDELETE, {
            method: "DELETE",
          });
          if (!deleteResponse.ok) {
            throw new Error("Error en la red");
          }
        } catch (error) {
          console.error("Hubo un problema con la eliminación:", error);
        }
      }

      const loanUpdate = document.createElement("p");
      loanUpdate.textContent = "Préstamo actualizado";
      loanUpdate.className = "mt-2";
      messageContainer.appendChild(loanUpdate);
    } else {
      const loanErrorUpdate = document.createElement("p");
      loanErrorUpdate.textContent = "Porfavor, rellene todos los campos";
      loanErrorUpdate.className = "mt-2";
      emptyContainer.appendChild(loanErrorUpdate);
    }
  });
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
  const diffMonths =
    (end.getFullYear() - start.getFullYear()) * 12 +
    (end.getMonth() - start.getMonth());
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

function addOneYear(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);

  const date = new Date(year, month - 1, day);

  date.setFullYear(date.getFullYear() + 1);

  const newDay = String(date.getDate()).padStart(2, "0");
  const newMonth = String(date.getMonth() + 1).padStart(2, "0");
  const newYear = date.getFullYear();

  return `${newDay}-${newMonth}-${newYear}`;
}

function addOneMonth(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);

  const date = new Date(year, month - 1, day);

  date.setMonth(date.getMonth() + 1);

  const newDay = String(date.getDate()).padStart(2, "0");
  const newMonth = String(date.getMonth() + 1).padStart(2, "0");
  const newYear = date.getFullYear();

  return `${newDay}-${newMonth}-${newYear}`;
}

function addOneWeek(dateString) {
  const [day, month, year] = dateString.split("-").map(Number);

  const date = new Date(year, month - 1, day);

  date.setDate(date.getDate() + 7);

  const newDay = String(date.getDate()).padStart(2, "0");
  const newMonth = String(date.getMonth() + 1).padStart(2, "0");
  const newYear = date.getFullYear();

  return `${newDay}-${newMonth}-${newYear}`;
}
