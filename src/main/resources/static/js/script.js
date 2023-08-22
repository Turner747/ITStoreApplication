window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {

        if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
            document.body.classList.toggle('sb-sidenav-toggled');
        }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

function addRowFromSelect(idSelect, idTable, th_input) {
    var selectedData = $(idSelect + " option:selected").text();
    var selectedDataId = $(idSelect + " option:selected").attr('id');
    var rows = parseInt($(idTable + ' tr').length - 1);
    if (selectedData.toString() !== "" && ($(idTable).find('#' + selectedDataId).length === 0)) {

        var input = '<input hidden="true" name=' + th_input + ' type="text" th:field="*{' + th_input + '[' + rows + '].id}" value="' + selectedDataId + '"  th:value="' + selectedDataId + '"  th:text="' + selectedData + '" ></input>';
        var td = '<td>' + selectedData + ' ' + input + '</td>';
        $(idTable + " > tBody:last").append('<tr id="' + selectedDataId + '"> ' + td + '</tr>');
    }

    $(idTable).on('click', 'tr', function () {
        $(this).addClass("clicked").siblings().removeClass("clicked");
    });
}

function removeRow(tableId) {

    //var rowCount = $("#tablePontosDeDistribuicao tr").length;
    var rowCount = $(tableId + " tr").length;

    if (rowCount > 1) {

        var selected = $(tableId + ' > tbody > tr.clicked');

        if (selected !== null && selected.length > 0) {
            selected.remove();
        } else {
            $(tableId + ' tr:last').remove();
        }
    }
}

function addOrderLine(){
    // Clone the template row
    var templateRow = document.getElementById("templateRow").cloneNode(true);

    // Find the table body where you want to append the new row
    var tableBody = document.querySelector(".table tbody");

    // Replace "__INDEX__" placeholders with the next index
    var newIndex = tableBody.childElementCount; // Calculate the next index
    templateRow.innerHTML = templateRow.innerHTML.replace(/__INDEX__/g, newIndex);

    // Append the modified template row to the table body
    tableBody.appendChild(templateRow);

    // Show the new row (it was initially hidden)
    templateRow.style.display = "";

    // Add an event listener to the remove button if needed
    var removeButton = templateRow.querySelector(".remove-row");
    removeButton.addEventListener("click", function () {
        tableBody.removeChild(templateRow);
    });

    // Update product price and total price based on user interactions (you can add this logic as needed)
    // ...

    // Optionally, you can reset any user selections in the new row
    // ...
}

function removeOrderLine(){
    window.alert("remove line");
}