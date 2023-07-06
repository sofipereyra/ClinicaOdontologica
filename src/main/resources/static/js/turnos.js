window.addEventListener('load', (e) => {
    try {
        listTurnos();
        listPacients();
        listOdontologos();
    } catch (error) {
        showError(error);
    }
})

function listTurnos(){
    fetch(API_BASE + '/turnos')
        .then((res) => {
            if(res.status != 200) throw new Error('No se han podido cargar los turnos')
            return res.json();
        })
        .then((data) => {
            let turnosRow = document.querySelector("#turnoTableBody");
            turnosRow.innerHTML = '';
            data.map((turno)=>{
                turnosRow.innerHTML += `
                    <tr>
                        <td>${turno.paciente}</td>
                        <td>${turno.odontologo}</td>
                        <td>${turno.fecha}</td>
                        <td>
                            <button id="edit" onclick="handleEdit(${turno.id})" >
                                <img src="./assets/edit.svg" id="edit-img" alt="edit icon" srcset="">
                            </button>
                            <button id="btnDelete" onclick="deleteTurno(${turno.id})">
                                <img src="./assets/Delete.svg" id="delete-img" alt="trash icon" srcset="">
                            </button>
                        </td>
                    </tr>`
            })
        })
        .catch((error) => {
            showError(error);
        });
}

function listPacients(){
    fetch(API_BASE + '/pacientes')
        .then((res) => {
            if(res.status != 200) throw new Error('No se han podido cargar los pacientes')
            return res.json();
        })
        .then((data) => {
            let pacienteSelect = document.querySelector("#pacientes");
            if(pacienteSelect){
                pacienteSelect.innerHTML = ' ';
                pacienteSelect.innerHTML += `<option value="0">Selecciona el paciente</option>`;
                data.map((pacient)=>{
                    pacienteSelect.innerHTML += `<option value="${pacient.id}">${ pacient.nombre + " " + pacient.apellido}</option>`
                })
            }
            let pacienteSelectEdit = document.querySelector("#pacientesEdit");
            if(pacienteSelectEdit){
                pacienteSelectEdit.innerHTML = ' ';
                pacienteSelectEdit.innerHTML += `<option value="0">Selecciona el paciente</option>`;
                data.map((pacient)=>{
                    pacienteSelectEdit.innerHTML += `<option value="${pacient.id}">${ pacient.nombre + " " + pacient.apellido}</option>`
                })
            }
        })
        .catch((error) => {
            showError(error);
        });
}

function listOdontologos(){
    const settings = {
        method: 'GET'
      }
    fetch(API_BASE + '/odontologos', settings)
        .then((res) => {
            if(res.status != 200) throw new Error('No se han podido cargar los odontólogos')
            return res.json()
        })
        .then((data) => {
            let odontologoSelect = document.querySelector("#odontologos");
            if(odontologoSelect){
                odontologoSelect.innerHTML = ' ';
                odontologoSelect.innerHTML += `<option value="0">Selecciona el odontologo</option>`;
                data.map((odontologo)=>{
                    odontologoSelect.innerHTML += `<option value="${odontologo.id}">${ odontologo.nombre + " " + odontologo.apellido}</option>`
                })
            }
            let odontologoSelectEdit = document.querySelector("#odontologosEdit");
            if(odontologoSelectEdit){
                odontologoSelectEdit.innerHTML = ' ';
                odontologoSelectEdit.innerHTML += `<option value="0">Selecciona el odontologo</option>`;
                data.map((odontologo)=>{
                    odontologoSelectEdit.innerHTML += `<option value="${odontologo.id}">${ odontologo.nombre + " " + odontologo.apellido}</option>`
                })
            }

        })
        .catch((error) => {
            showError(error);
        }); 
}

function addTurno(dataTurno){
    let settings = {
        method : "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataTurno) 
    };
    
    fetch(`${API_BASE}/turnos/registrar`, settings)
        .then(res => {
            if(res.status != 200 && res.status != 201) throw new Error('No se pudo agregar el turno') 
            return res.json();
        })
        .then(data => {
            listTurnos();
        })
        .catch(error => {
           showError(error);
        });
}

function searchTurno(id){
    fetch(`${API_BASE}/turnos/${id}`)
    .then(res => {
        if(res.status != 200) throw new Error('No se pudo encontrar el turno'); 
        return res.json();
    })
    .then((data) => {
        let turnoRow = document.querySelector("#turnoTableBody");
        turnoRow.innerHTML = '';
        turnoRow.innerHTML += `
                <tr>
                    <td>${data.paciente}</td>
                    <td>${data.odontologo}</td>
                    <td>${data.fecha}</td>
                    <td>
                        <button id="edit" onclick="handleEdit(${data.id})" >
                            <img src="./assets/edit.svg" id="edit-img" alt="edit icon" srcset="">
                        </button>
                        <button id="btnDelete" onclick="deleteTurno(${data.id})">
                            <img src="./assets/Delete.svg" id="delete-img" alt="trash icon" srcset="">
                        </button>
                    </td>
                </tr>`
    }).catch(error => {
        showError(error);
    });
};

function editTurno(dataTurno){

    let settings = {
        method : "PUT",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataTurno)
    }

    fetch(`${API_BASE}/turnos/actualizar`, settings) 
    .then((res) =>  {
        if(res.status != 200) throw new Error('No se pudo editar el Turno');
        return res.json();
    }).then((data) => {
        listTurnos();
    })
    .catch(error => {
        showError(error);
    });
};

function deleteTurno(id){
    let settings = {
        method : "DELETE",
        headers: {
            'Content-Type': 'application/json'
        },
        body : ""
    }

    fetch(`${API_BASE}/turnos/eliminar/${id}`, settings)
    .then((res) => {
        if(res.status != 200) throw new Error('No se pudo eliminar el turno');
        if(res.status === 204) {
            success.innerHTML = `<div class="exito">
                                        <h4>Eliminado con éxito!</h4>
                                    <div>`
        }
    })
    .then((data) => listTurnos())
    .catch(error => {
        showError(error);
    });
};

btnAdd.addEventListener('click', async (e) => {
    e.preventDefault();
    try {
        let paciente = document.querySelector("#pacientes"),
        odontologo = document.querySelector("#odontologos"),
        fecha = document.querySelector("#fecha")
        
        let pacienteEncontrado = await searchPacient(paciente.value);
        let odontologoEncontrado = await searchOdontologo(odontologo.value);
        let formatFecha = fecha.value.split("T").join(" ");

        let dataTurno = {
            paciente : pacienteEncontrado,
            odontologo : odontologoEncontrado,
            fecha : formatFecha
        }
        
        addTurno(dataTurno);
        
    } catch (error) {
        showError(error);
    }
});

formSearch.addEventListener('submit',(e) => {
    e.preventDefault();
    try {
        const searchInput = document.querySelector('.search-input');
        if(isNaN(searchInput.value)) throw new Error('Debe ingrsesarse un número');
        searchTurno(searchInput.value);
    } catch (error) {
        showError(error);
    }
})

function handleEdit(id){
    try {
        listPacients();
        listOdontologos();
        formAdd.style.display = "none";
        formEdit.style.display = "block";
        if(btnEdit){
            btnEdit.addEventListener('click', async (e) => {
                e.preventDefault();
    
                let paciente = document.querySelector("#pacientesEdit"),
                odontologo = document.querySelector("#odontologosEdit"),
                fecha = document.querySelector("#fechaEdit")
    
                let pacienteEncontrado = await searchPacient(paciente.value);
                let odontologoEncontrado = await searchOdontologo(odontologo.value);
                let formatFecha = fecha.value.split("T").join(" ");
        
                let dataTurno = {
                    id: id,
                    paciente : pacienteEncontrado,
                    odontologo : odontologoEncontrado,
                    fecha : formatFecha
                }
                editTurno(dataTurno)
            })
        }
        
    } catch (error) {
        showError(error);
    }

}

btnReload.addEventListener('click',()=>{
    try {
        listTurnos();
        formEdit.style.display = "none";
        formAdd.style.display = "block";
    } catch (error) {
        showError(error);
    }
})

function searchPacient(id){
    return fetch(`${API_BASE}/pacientes/${id}`)
    .then(res => {
        if(res.status !== 200) throw new Error('No se pudo encontrar el paciente') ;
        return res.json();
    })
    .then((data) => {
        return data;
    }).catch(error => {
        showError(error);
    });
};

function searchOdontologo(id){
    return fetch(`${API_BASE}/odontologos/${id}`)
    .then(res => {
        if(res.status != 200) throw new Error('No se pudo encontrar el odontólogo');
        return res.json();
    })
    .then((data) => {
        return data;
    }).catch((error) => {
        showError(error);
    }); 
};