const express = require('express');
const app = express();
const path = require('path');

// Configurar la carpeta estática
app.use('/static', express.static(path.join(__dirname, 'static')));

app.listen(8080, () => {
    console.log('Servidor escuchando en el puerto 8080');
});


