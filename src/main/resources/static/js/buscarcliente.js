let clientesPaginados = [];
let currentPage = 1;
const itemsPerPage = 10;

function confirmLogout(event) {
    event.preventDefault();
    const confirmed = confirm("Você deseja realmente sair da aplicação?");
    if (confirmed) {
        localStorage.clear();
        window.location.href = "/login";
    }
}

/* ✅ CORRIGE TEXTO COM ERRO DE ENCODING (CÃ³digo, AndrÃ©, FlorianÃ³polis) */
function fixMojibake(text) {
    if (!text) return '';
    return text
        .replace(/Ã¡/g, 'á').replace(/ÃÀ/g, 'Á').replace(/Ã /g, 'à')
        .replace(/Ã¢/g, 'â').replace(/Ã£/g, 'ã')
        .replace(/Ã©/g, 'é').replace(/Ãª/g, 'ê')
        .replace(/Ã­/g, 'í')
        .replace(/Ã³/g, 'ó').replace(/Ã´/g, 'ô').replace(/Ãµ/g, 'õ')
        .replace(/Ãº/g, 'ú')
        .replace(/Ã§/g, 'ç')
        .replace(/Âº/g, 'º').replace(/Âª/g, 'ª')
        .replace(/Ã‰/g, 'É').replace(/ÃŠ/g, 'Ê')
        .replace(/Ã/g, 'Í')
        .replace(/Ã"/g, 'Ó')
        .replace(/Ã‡/g, 'Ç')
        .replace(/Ã³/g, 'ó').replace(/â/g, "'")
        .replace(/â/g, "-").replace(/â¦/g, "...");
}

async function decodeBufferTry(buffer) {
    const encodings = ['iso-8859-1', 'windows-1252', 'latin1', 'utf-8'];
    for (const enc of encodings) {
        try {
            const decoder = new TextDecoder(enc);
            const text = decoder.decode(buffer);
            return { text, encoding: enc };
        } catch (e) {
            console.warn('TextDecoder não suportou:', enc, e);
        }
    }
    const bytes = new Uint8Array(buffer);
    let fallback = '';
    for (let i = 0; i < bytes.length; i++) {
        fallback += String.fromCharCode(bytes[i]);
    }
    return { text: fallback, encoding: 'binary-fallback' };
}

async function searchCliente() {
    const codigo = document.getElementById('codigo').value.trim();
    const nome = document.getElementById('nome').value.trim();
    const token = localStorage.getItem('token');

    const params = new URLSearchParams();
    if (codigo) params.append('id', codigo);
    if (nome) params.append('nome', nome);

    try {
        const response = await fetch(`/cliente/buscar?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Erro ao buscar clientes');
        }

        const buffer = await response.arrayBuffer();
        const decoded = await decodeBufferTry(buffer);

        let clientes;
        try {
            clientes = JSON.parse(decoded.text);
        } catch (e) {
            // Try to fix encoding and parse again
            const fixedText = fixMojibake(decoded.text);
            clientes = JSON.parse(fixedText);
        }

        let filtrados = clientes;
        if (nome) {
            const nomeLower = nome.toLowerCase();
            filtrados = clientes.filter(c => c.nome && c.nome.toLowerCase().includes(nomeLower));
        }
        if (codigo) {
            filtrados = filtrados.filter(c => String(c.id).includes(codigo));
        }

        const ordenarPor = document.getElementById('ordenarPor').value;

        if (ordenarPor === 'nome') {
            filtrados.sort((a, b) => (a.nome || '').localeCompare(b.nome || ''));
        }

        if (ordenarPor === 'cidade') {
            filtrados.sort((a, b) => (a.cidade || '').localeCompare(b.cidade || ''));
        }

        populateResultsTable(filtrados);
    } catch (error) {
        console.error(error);
        M.toast({ html: `Erro ao buscar clientes: ${error}`, classes: 'red' });
    }
}

function populateResultsTable(clientes) {
    clientesPaginados = clientes;
    currentPage = 1;
    renderPage();
}

function renderPage() {
    const tbody = document.querySelector('#resultsTable tbody');
    tbody.innerHTML = '';

    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const pageItems = clientesPaginados.slice(startIndex, endIndex);

    if (pageItems.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Nenhum cliente encontrado</td></tr>';
        return;
    }

    pageItems.forEach(cliente => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${cliente.id}</td>
            <td>
                ${cliente.nome}
                ${cliente.observacao?.toLowerCase() === "inativo" ? '<span class="badge-inativo">INATIVO</span>' : ''}
            </td>
            <td>
                ${cliente.cidade || ''}
            </td>
            <td>
                <button class="action-button" onclick="editcliente('${cliente.id}')">
                    <span class="material-icons">edit</span>
                </button>
                <button class="action-button" onclick="confirmDelete('${cliente.id}')">
                    <span class="material-icons">delete</span>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });

    const totalPages = Math.ceil(clientesPaginados.length / itemsPerPage);
    document.getElementById('pageInfo').textContent = `Página ${currentPage} de ${totalPages}`;
    document.getElementById('prevButton').disabled = currentPage === 1;
    document.getElementById('nextButton').disabled = currentPage === totalPages;
}

function changePage(direction) {
    const totalPages = Math.ceil(clientesPaginados.length / itemsPerPage);
    currentPage += direction;

    if (currentPage < 1) currentPage = 1;
    if (currentPage > totalPages) currentPage = totalPages;

    renderPage();
}

function clearSearch() {
    document.getElementById('codigo').value = '';
    document.getElementById('nome').value = '';
    document.querySelector('#resultsTable tbody').innerHTML = '';
    clientesPaginados = [];
    currentPage = 1;
    document.getElementById('pageInfo').textContent = 'Página 1';
    document.getElementById('prevButton').disabled = true;
    document.getElementById('nextButton').disabled = true;
}

async function editcliente(codigo) {
    const token = localStorage.getItem('token');

    try {
        const response = await fetch(`/cliente/${codigo}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            M.toast({ html: `Erro ao buscar cliente: ${response.statusText}`, classes: 'red' });
            return;
        }

        const cliente = await response.json();

        localStorage.setItem('clienteParaEditar', JSON.stringify(cliente));

        window.location.href = '/alterarcliente';

    } catch (error) {
        console.error('Erro ao buscar cliente:', error);
        M.toast({ html: `Erro inesperado ao buscar os dados do cliente.`, classes: 'red' });
    }
}

async function confirmDelete(codigo) {
    const confirmed = confirm("Tem certeza que deseja excluir este cliente?");
    if (!confirmed) return;

    const token = localStorage.getItem('token');

    try {
        const response = await fetch(`/cliente/${codigo}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            M.toast({ html: 'Cliente excluído com sucesso!', classes: 'green' });
            searchCliente();
        } else {
            const errorData = await response.json();
            M.toast({ html: `Erro ao excluir o cliente: ${response.statusText}`, classes: 'red' });
            console.log("Erro ao excluir o cliente: " + (errorData.message || response.statusText));
        }
    } catch (error) {
        console.error("Erro ao excluir o cliente:", error);
        M.toast({ html: `Erro inesperado ao tentar excluir o cliente: ${error.message}`, classes: 'red' });
    }
}

async function exportarClientesCSV() {
    const codigo = document.getElementById('codigo').value.trim();
    const nome = document.getElementById('nome').value.trim();
    const token = localStorage.getItem('token');

    const params = new URLSearchParams();
    if (codigo) params.append('id', codigo);
    if (nome) params.append('nome', nome);

    try {
        const response = await fetch(`/cliente/buscar?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            const txt = await response.text().catch(() => null);
            console.error('Resposta não OK:', response.status, txt);
            throw new Error("Erro ao buscar clientes: " + response.status);
        }

        const buffer = await response.arrayBuffer();
        const decoded = await decodeBufferTry(buffer);

        let clientes;
        try {
            clientes = JSON.parse(decoded.text);
        } catch (e) {
            const tryFixed = fixMojibake(decoded.text);
            try {
                clientes = JSON.parse(tryFixed);
            } catch (e2) {
                try {
                    clientes = await response.clone().json();
                } catch (e3) {
                    console.error('Falha ao parsear JSON do servidor.');
                    throw new Error('Não foi possível interpretar a resposta do servidor como JSON.');
                }
            }
        }

        if (!clientes || clientes.length === 0) {
            M.toast({ html: 'Nenhum cliente para exportar.', classes: 'orange' });
            return;
        }

        const usuario = (JSON.parse(localStorage.getItem('usuario'))?.nome) || 'Usuário Desconhecido';

        const agora = new Date();
        const dataExportacao = agora.toLocaleDateString('pt-BR');
        const horaExportacao = agora.toLocaleTimeString('pt-BR');

        // ✅ Use semicolons as separators (standard for Brazilian CSV)
        let csvLines = [];
        csvLines.push(`Exportado por;${fixMojibake(usuario)}`);
        csvLines.push(`Data;${dataExportacao}`);
        csvLines.push(`Hora;${horaExportacao}`);
        csvLines.push(''); // Empty line
        csvLines.push('Código;Nome;Cidade'); // Header with semicolons

        clientes.forEach(cliente => {
            const id = cliente.id ?? '';
            const nomeField = fixMojibake(String(cliente.nome ?? ''));
            const cidadeField = fixMojibake(String(cliente.cidade ?? ''));

            // Escape semicolons in data by enclosing in quotes if they contain semicolons
            let safeNome = nomeField;
            let safeCidade = cidadeField;

            // If field contains semicolon, enclose entire field in quotes
            if (nomeField.includes(';')) {
                safeNome = `"${nomeField}"`;
            }
            if (cidadeField.includes(';')) {
                safeCidade = `"${cidadeField}"`;
            }

            csvLines.push(`${id};${safeNome};${safeCidade}`);
        });

        const csv = csvLines.join('\r\n');
        const csvComBOM = '\uFEFF' + csv; // UTF-8 BOM for Excel

        // Create and download the file
        const blob = new Blob([csvComBOM], { type: 'text/csv;charset=utf-8;' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `clientes_${agora.getTime()}.csv`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(url);

        M.toast({ html: 'CSV exportado com sucesso!', classes: 'green' });

    } catch (error) {
        console.error('Erro exportarClientesCSV:', error);
        M.toast({ html: 'Erro ao exportar CSV. Veja console para detalhes.', classes: 'red' });
    }
}
document.addEventListener('DOMContentLoaded', function () {
    const elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);
});