window.addEventListener('load', (e) => {
    try {
        listOdontologos();
    } catch (error) {
        showError(error);
    }
    
})

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
            let odontologoRow = document.querySelector("#odontologoTableBody");
            odontologoRow.innerHTML = " ";
            data.map((odont)=>{
                odontologoRow.innerHTML += `
                    <tr>
                        <td>${odont.nombre}</td>
                        <td>${odont.apellido}</td>
                        <td>${odont.matricula}</td>
                        <td>
                            <button id="edit" onclick="handleEdit(${odont.id})" >
                                <img src="./assets/edit.svg" id="edit-img" alt="edit icon" srcset="">
                            </button>
                            <button id="btnDelete" onclick="deleteOdontologo(${odont.id})">
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

function addOdontologo(dataOdontologo){
    let settings = {
        method : "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataOdontologo) 
    };
    
    fetch(`${API_BASE}/odontologos/registrar`, settings)
        .then(res => {
            if(res.status != 200 && res.status != 201) throw new Error('No se pudo agregar el odontólogo') 
            return res.json();
        })
        .then(data => {
            listOdontologos();
            resetUploadForm();
        })
        .catch(error => {
            showError(error);
        });
}

function searchOdontologo(id){
    fetch(`${API_BASE}/odontologos/${id}`)
    .then(res => {
        if(res.status != 200) throw new Error('No se pudo encontrar el odontólogo');
        return res.json();
    })
    .then((data) => {
        let odontologoRow = document.querySelector("#odontologoTableBody");
        odontologoRow.innerHTML = '';
        odontologoRow.innerHTML += `
            <tr>
                <td>${data.nombre}</td>
                <td>${data.apellido}</td>
                <td>${data.matricula}</td>
                <td>
                    <button id="edit" onClick="handleEdit(${data.id})" >
                        <img src="./assets/edit.svg" id="edit-img" alt="edit icon" srcset="">
                    </button>
                    <button id="btnDelete" onClick="deleteOdontologo(${data.id})">
                        <img src="./assets/Delete.svg" id="delete-img" alt="trash icon" srcset="">
                    </button>
                </td>
            </tr>`
        resetUploadForm();
    }).catch((error) => {
        showError(error);
    }); 
};

function editOdontologo(dataOdontologo){
    console.log(dataOdontologo);
    let settings = {
        method : "PUT",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataOdontologo)
    }

    fetch(`${API_BASE}/odontologos/actualizar`, settings) 
    .then((res) =>  {
        if(res.status != 200 && res.status != 201) throw new Error('No se pudo agregar el odontólogo') 
        return res.json();
    }).then((data) => {
        listOdontologos();
        resetEditForm();
    })
    .catch((error) => {
        showError(error);
    });
};

const deleteOdontologo = (id) => {
    let settings = {
        method : "DELETE",
        headers: {
            'Content-Type': 'application/json'
        },
        body : ""
    }

    fetch(`${API_BASE}/odontologos/eliminar/${id}`, settings)
    .then((res) => {
        if(res.status != 200) throw new Error('No se pudo eliminar el Odontólogo')
        if(res.status === 204) {
            success.innerHTML = `<div class="exito">
                                        <h4>Eliminado con éxito!</h4>
                                    <div>`
            listadoOdon.innerHTML = "";
        }
    })
    .then((data) => {
        listOdontologos();
    })
    .catch((error) => {
        showError(error);
    });
};

if(btnAdd){
    try {
        btnAdd.addEventListener('click', (e) => {
            e.preventDefault();
            let nombreOdon = document.querySelector("#nombre"),
            apellidoOdon = document.querySelector("#apellido"),
            matricula = document.querySelector("#matricula")
    
            let datosOdontologo = {
                nombre : nombreOdon.value,
                apellido : apellidoOdon.value,
                matricula : matricula.value 
            }
    
            addOdontologo(datosOdontologo);
        });
    } catch (error) {
        showError(error);
    }
}

formSearch.addEventListener('submit',(e) => {
    e.preventDefault();
    try {
        const searchInput = document.querySelector('.search-input');
        if(isNaN(searchInput.value)) throw new Error('Debe ingrsesarse un número');
        searchOdontologo(searchInput.value);
    } catch (error) {
        showError(error);
    }
})

function handleEdit(id){
    try {
        formAdd.style.display = "none";
        formEdit.style.display = "block";
    
        if(btnEdit){
            btnEdit.addEventListener('click', (e) => {
                e.preventDefault();
                let nombreOdon = document.querySelector("#nombreEdit"),
                apellidoOdon = document.querySelector("#apellidoEdit"),
                matricula = document.querySelector("#matriculaEdit")
    
                let dataOdontologo = {
                    id : id,
                    nombre : nombreOdon.value,
                    apellido : apellidoOdon.value,
                    matricula : matricula.value 
                };
            
                editOdontologo(dataOdontologo);
            })
        }
    } catch (error) {
        showError(error);
    }
    
}

btnReload.addEventListener('click',()=>{
    try {
        listOdontologos();
        formEdit.style.display = "none";
        formAdd.style.display = "block";
    } catch (error) {
        showError(error);
    }
})

function resetUploadForm(){
    try {
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#matricula').value = "";
    } catch (error) {
        showError(error);
    }
}

function resetEditForm(){
    try {
        document.querySelector('#nombreEdit').value = "";
        document.querySelector('#apellidoEdit').value = "";
        document.querySelector('#matriculaEdit').value = "";
    } catch (error) {
        showError(error);
    }
}