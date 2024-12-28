const express = require('express')
const app = express()
const port = 3000
const path = require('path')
/*app.use(express.static('public'))*/

app.get('/', (req, res) => {
    res.send('Hello World!')
})
/*app.use('/public', express.static(path.join(__dirname, 'C:/project/db/music')));*/
app.listen(port, () => {
    console.log('Server running on http://localhost:3000');
});
console.log("__dir",`file://${path.join(__dirname, "../dist/index.html")}`);
