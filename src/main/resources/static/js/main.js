const pacienteCard = document.querySelector(".pacientes");
const odontologoCard = document.querySelector(".odontologos");
const turnoCard = document.querySelector(".turnos");
const domicilioCard = document.querySelector(".domicilio");
const arrow = document.getElementById("arrow");

const API_BASE = "http://localhost:8080";
const err = document.getElementById('error');
const table = document.querySelector('table');
const success = document.getElementById('success');
const btnAdd = document.getElementById('btnAdd');
const btnEdit = document.getElementById('btnEdit');         
const formAdd = document.querySelector('.add-form-section');
const formEdit = document.querySelector('.edit-form-section');
const formSearch = document.getElementById('search-form');
const btnReload = document.getElementById('btn-reload'); 

if(pacienteCard){
    pacienteCard.addEventListener("click", ()=>{
        location.replace("./paciente.html");
    });
}
if(odontologoCard){
odontologoCard.addEventListener("click", ()=>{
    location.replace("./odontologo.html");
});}
if(turnoCard){        
turnoCard.addEventListener("click", ()=>{
    location.replace("./turno.html");
});}
if(arrow){
    arrow.addEventListener("click", () => {
        location.replace("/");
    })
}

function showError(error){
    err.style.display = "block";
    err.classList.add("error");
    err.innerHTML = `<img src="./assets/close.svg" id="close-error" alt="close error icon">
        <p>${error}</p>
        <img src="./assets/error.svg" id="error-img" alt="Error image">`;

    const closeError = document.getElementById('close-error');

    closeError.addEventListener('click', () => {
        err.style.display = "none";
    })
}